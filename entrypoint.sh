#!/bin/bash
set +x
set -e

## ~~~ DO NOT MODIFY ~~~ ##
SOURCE="${BASH_SOURCE[0]}"
while [ -h "$SOURCE" ]; do
  DIR="$( cd -P "$( dirname "$SOURCE" )" && pwd )"
  SOURCE="$(readlink "$SOURCE")"
  [[ ${SOURCE} != /* ]] && SOURCE="$DIR/$SOURCE"
done
CONF_DIR="$( cd -P "$( dirname "$SOURCE" )" && pwd )"
cd ${CONF_DIR}
export CONFIG_HOME=$CONF_DIR
## ~~~~~~~~~~~~~~~~~~~~~ ##

#default java options for container
JAVA_OPTS="-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1"

#add extra java options
if [[ -n "${EXTRA_JAVA_OPTS}" ]]; then
    JAVA_OPTS="$JAVA_OPTS $EXTRA_JAVA_OPTS"
fi

exec java ${JAVA_OPTS} -jar $HOME/service/service.jar "$@"