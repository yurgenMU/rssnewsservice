
# Derived from official mysql image (our base image)
FROM mysql
# Add a database
# Add the content of the sql-scripts/ directory to your image
# All scripts in docker-entrypoint-initdb.d/ are automatically
# executed during container startup

COPY news-service/src/main/resources/schema.sql /docker-entrypoint-initdb.d/
COPY news-service/src/main/resources/data.sql /docker-entrypoint-initdb.d/data.sql

ENTRYPOINT ["docker-entrypoint.sh"]
EXPOSE 3306
CMD ["mysqld"]