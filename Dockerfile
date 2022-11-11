FROM centos:centos8.4.2105
LABEL maintainer="Dhiraj"
#docker run -it --privileged centos:centos8.4.2105 /bin/bash
#Installing Centos 8
RUN cd /etc/yum.repos.d/
RUN sed -i 's/mirrorlist/#mirrorlist/g' /etc/yum.repos.d/CentOS-*
RUN sed -i 's|#baseurl=http://mirror.centos.org|baseurl=http://vault.centos.org|g' /etc/yum.repos.d/CentOS-*
#CMD /bin/bash

RUN cd ..
RUN cd .. # back to home directory
RUN dnf update -y
#RUN dnf upgrade -y
#RUN dnf install nginx -y

#Installing Java 8 and maven latest version
RUN dnf -y remove java
RUN dnf install -y \
       java-1.8.0-openjdk \
       java-1.8.0-openjdk-devel

RUN dnf install -y maven
RUN dnf install -y curl
RUN dnf install -y unzip
RUN dnf -y install wget
RUN dnf install epel-release -y

RUN dnf install -y policycoreutils-python-utils

#To download WildFly
RUN wget https://github.com/wildfly/wildfly/releases/download/26.1.2.Final/wildfly-26.1.2.Final.tar.gz

RUN tar xvf wildfly-26.1.2.Final.tar.gz
RUN mv wildfly-26.1.2.Final /opt/wildfly

#RUN tar xvf wildfly-${WILDFLY_RELEASE}.tar.gz
#RUN mv wildfly-${WILDFLY_RELEASE} /opt/wildfly

RUN groupadd --system wildfly
RUN useradd -s /sbin/nologin --system -d /opt/wildfly  -g wildfly wildfly

RUN mkdir /etc/wildfly

RUN cp opt/wildfly/docs/contrib/scripts/systemd/wildfly.conf etc/wildfly/
RUN cp opt/wildfly/docs/contrib/scripts/systemd/wildfly.service etc/systemd/system/
RUN cp opt/wildfly/docs/contrib/scripts/systemd/launch.sh opt/wildfly/bin/
RUN chmod +x /opt/wildfly/bin/launch.sh

RUN chown -R wildfly:wildfly /opt/wildfly

#RUN  semanage fcontext  -a -t bin_t  "/opt/wildfly/bin(/.*)?"
RUN  restorecon -Rv /opt/wildfly/bin/

#Copy Local(Host) WAR into Docker Image

#COPY /target/CleanTasks.war /opt/wildfly/standalone/deployments
RUN /opt/wildfly/bin/add-user.sh -a 'user' -p 'admin!1' --silent

#EXPOSE 3306 8085 9090 1883 5672 8161 61613 61614 61616
# To run wildfly Server on Docker uncomment '&' to run in background
COPY target/CleanTasks.war /opt/wildfly/standalone/deployments
RUN ls /opt/wildfly/standalone/deployments

#CMD ["/opt/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
RUN /opt/wildfly/bin/standalone.sh -b=0.0.0.0 -bmanagment=0.0.0.0 &

#ENTRYPOINT ["clean", "install", "cargo:run", "-f", "pom.xml"]