#
#  Copyright (C) 2013-2016 MariaDB Corporation AB
#
#  Redistribution and use is allowed according to the terms of the New
#  BSD license.
#  For details see the COPYING-CMAKE-SCRIPTS file.
#
MACRO(GET_CONNECTOR_PACKAGE_NAME name)
# check if we have 64bit
IF(SIZEOF_VOIDP EQUAL 8)
  SET(IS64 1)
ENDIF()

SET (PLATFORM_NAME ${CMAKE_SYSTEM_NAME})
SET (MACHINE_NAME ${CMAKE_SYSTEM_PROCESSOR})
SET (CONCAT_SIGN "-")

IF(CMAKE_SYSTEM_NAME MATCHES "Windows")
  SET(PLATFORM_NAME "win")
  SET(CONCAT_SIGN "")
  IF(IS64)
    IF(CMAKE_C_COMPILER_ARCHITECTURE_ID)
      STRING(TOLOWER "${CMAKE_C_COMPILER_ARCHITECTURE_ID}" MACHINE_NAME)
    ELSE()
      SET(MACHINE_NAME x64)
    ENDIF()
  ELSE()
    SET(MACHINE_NAME "32")
  ENDIF()
ENDIF()

SET(product_name "mysql-connector-c-${CPACK_PACKAGE_VERSION}-${PLATFORM_NAME}${CONCAT_SIGN}${MACHINE_NAME}")
STRING(TOLOWER ${product_name} ${name})
ENDMACRO()
