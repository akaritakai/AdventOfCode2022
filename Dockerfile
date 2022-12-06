FROM gradle:7.6.0-jdk17

WORKDIR "/opt/aoc"

COPY . .

ENTRYPOINT ["gradle", "run"]