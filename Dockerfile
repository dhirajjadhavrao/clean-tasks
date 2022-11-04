FROM centos:centos8.4.2105
#docker run -it --privileged centos:centos8.4.2105 /bin/bash
#Installing Centos 8
RUN cd /etc/yum.repos.d/
RUN sed -i 's/mirrorlist/#mirrorlist/g' /etc/yum.repos.d/CentOS-*
RUN sed -i 's|#baseurl=http://mirror.centos.org|baseurl=http://vault.centos.org|g' /etc/yum.repos.d/CentOS-*
#CMD /bin/bash

RUN cd ..
RUN cd .. # back to home directory
RUN dnf update -y
RUN dnf upgrade -y
RUN dnf install epel-release -y
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

RUN wget https://github.com/wildfly/wildfly/releases/download/26.1.2.Final/wildfly-26.1.2.Final.tar.gz
#WILDFLY_RELEASE=26.1.0.Final \
#wget https://github.com/wildfly/wildfly/releases/download/${WILDFLY_RELEASE}/wildfly-${WILDFLY_RELEASE}.tar.gz

#WILDFLY_RELEASE=$(curl -s https://api.github.com/repos/wildfly/wildfly/releases/latest|grep tag_name|cut -d '"' -f 4) \
#wget https://github.com/wildfly/wildfly/releases/download/${WILDFLY_RELEASE}/wildfly-${WILDFLY_RELEASE}.tar.gz

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

RUN  chown -R wildfly:wildfly /opt/wildfly

RUN dnf install -y policycoreutils-python-utils

#RUN  semanage fcontext  -a -t bin_t  "/opt/wildfly/bin(/.*)?"
RUN  restorecon -Rv /opt/wildfly/bin/

ADD /target/CleanTasks.war /opt/wildfly/standalone/deployments
#RUN /bin/bash /opt/wildfly/bin/standalone.sh -b=0.0.0.0 -bmanagment=0.0.0.0 --run on the same terminal
#RUN /opt/wildfly/bin/add-user.sh -u 'admin' -p 'admin!1'
RUN /opt/wildfly/bin/add-user.sh -a 'user' -p 'admin!1'

RUN /opt/wildfly/bin/standalone.sh -b=0.0.0.0 -bmanagment=0.0.0.0 & #-- to run run in detached mode

#CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0", "-c","standalone-microprofile.xml"]
#ADD target/lgt.war /opt/wildfly/standalone/deployments
#No use
#RUN  systemctl start wildfly
#RUN  systemctl enable wildfly
#RUN systemctl status wildfly

EXPOSE 9090
#FROM openjdk:8
#EXPOSE 8080
#ADD target/cleanbank.jar cleanbank.jar
#ENTRYPOINT ["java", "-jar", "/cleanbank.jar"]