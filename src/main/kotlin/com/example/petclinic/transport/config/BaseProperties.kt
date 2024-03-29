package com.example.petclinic.transport.config

import lombok.Getter

@Getter
abstract class BaseProperties(
    bootstrapServers: String,
    ) {
    open var props = mutableMapOf<String, Any>(
        "bootstrap.servers" to bootstrapServers,
        "key.serializer" to "org.apache.kafka.common.serialization.StringSerializer",
        "value.serializer" to "org.apache.kafka.common.serialization.StringSerializer",
        "value.serializer" to "org.springframework.kafka.support.serializer.JsonSerializer",
        "security.protocol" to "PLAINTEXT",
        "acks" to "-1",
        "batch.size" to "16384",
        "bootstrap.servers" to "localhost:9092",
        "buffer.memory" to "33554432",
        "client.dns.lookup" to "use_all_dns_ips",
        "client.id" to "producer-1",
        "compression.type" to "none",
        "connections.max.idle.ms" to "540000",
        "delivery.timeout.ms" to "120000",
        "enable.idempotence" to "true",
        "interceptor.classes" to "[]",
        "key.serializer" to "class org.apache.kafka.common.serialization.StringSerializer",
        "linger.ms" to "0",
        "max.block.ms" to 60000,
        "max.in.flight.requests.per.connection" to 5,
        "max.request.size" to 1048576,
        "metadata.max.age.ms" to 300000,
        "metadata.max.idle.ms" to 300000,
        "metric.reporters" to "[]",
        "metrics.num.samples" to 2,
        "metrics.recording.level" to "INFO",
        "metrics.sample.window.ms" to 30000,
        "partitioner.adaptive.partitioning.enable" to true,
        "partitioner.availability.timeout.ms" to 0,
        //"partitioner.class" to null,
        "partitioner.ignore.keys" to false,
        "receive.buffer.bytes" to 32768,
        "reconnect.backoff.max.ms" to 1000,
        "reconnect.backoff.ms" to 50,
        "request.timeout.ms" to 30000,
        "retries" to 2147483647,
        "retry.backoff.ms" to 100,
        //"sasl.client.callback.handler.class" to null,
        //"sasl.jaas.config" to null,
        "sasl.kerberos.kinit.cmd" to "/usr/bin/kinit",
        "sasl.kerberos.min.time.before.relogin" to 60000,
        // "sasl.kerberos.service.name" to null,
        "sasl.kerberos.ticket.renew.jitter" to 0.05,
        "sasl.kerberos.ticket.renew.window.factor" to 0.8,
    //  "sasl.login.callback.handler.class" to null,
        // "sasl.login.class" to null,
   // "sasl.login.connect.timeout.ms" to null,
   // "sasl.login.read.timeout.ms" to null,
        "sasl.login.refresh.buffer.seconds" to 300,
        "sasl.login.refresh.min.period.seconds" to 60,
        "sasl.login.refresh.window.factor" to 0.8,
        "sasl.login.refresh.window.jitter" to 0.05,
        "sasl.login.retry.backoff.max.ms" to 10000,
        "sasl.login.retry.backoff.ms" to 100,
        "sasl.mechanism" to "GSSAPI",
        "sasl.oauthbearer.clock.skew.seconds" to 30,
   // "sasl.oauthbearer.expected.audience" to null
    //"sasl.oauthbearer.expected.issuer" to null
        "sasl.oauthbearer.jwks.endpoint.refresh.ms" to 3600000,
        "sasl.oauthbearer.jwks.endpoint.retry.backoff.max.ms" to 10000,
        "sasl.oauthbearer.jwks.endpoint.retry.backoff.ms" to 100,
    //"sasl.oauthbearer.jwks.endpoint.url" to null,
        "sasl.oauthbearer.scope.claim.name" to "scope",
        "sasl.oauthbearer.sub.claim.name" to "sub",
   // "sasl.oauthbearer.token.endpoint.url" to null,
        "security.protocol" to "PLAINTEXT",
   // "security.providers" to null,
        "send.buffer.bytes" to 131072,
        "socket.connection.setup.timeout.max.ms" to 30000,
        "socket.connection.setup.timeout.ms" to 10000,
    //"ssl.cipher.suites" to null,
        "ssl.enabled.protocols" to "[TLSv1.2, TLSv1.3]",
        "ssl.endpoint.identification.algorithm" to "https",
   // "ssl.engine.factory.class" to null,
   //"ssl.key.password" to null,
        "ssl.keymanager.algorithm" to "SunX509",
   // "ssl.keystore.certificate.chain" to null,
   // "ssl.keystore.key" to null,
   // "ssl.keystore.location" to null,
   // "ssl.keystore.password" to null,
        "ssl.keystore.type" to "JKS",
        "ssl.protocol" to "TLSv1.3",
  //  "ssl.provider" to null,
  //  "ssl.secure.random.implementation" to null,
        "ssl.trustmanager.algorithm" to "PKIX",
  //  "ssl.truststore.certificates" to null,
   // "ssl.truststore.location" to null,
   // "ssl.truststore.password" to null,
        "ssl.truststore.type" to "JKS",
        "transaction.timeout.ms" to 60000,
  //  "transactional.id" to null,
        "value.serializer" to "class org.springframework.kafka.support.serializer.JsonSerializer"
    )
}