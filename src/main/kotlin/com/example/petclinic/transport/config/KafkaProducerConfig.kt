package com.example.petclinic.transport.config

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * class KafkaProducerConfig
 */
@Configuration
class KafkaProducerConfig {
   private val bootstrapAddress: String = "localhost:9092"
   /* @Bean
    fun producerFactory(): ProducerFactory<String, String> {
        val configProps =  OwnerProducer().props
        return DefaultKafkaProducerFactory(configProps)
    }*/

   /* @Bean
    fun kafkaTemplate(): KafkaTemplate<String, String> {
        return KafkaTemplate(producerFactory())
    }*/
}

class OwnerProducer: BaseProperties("localhost:9092"){}
class OwnerConsumer:  BaseProperties("localhost:9092"){
  init{
      this.props.put("auto.offset.reset" , "earliest")
      this.props.put("group.id", "someGroup")
  }
}