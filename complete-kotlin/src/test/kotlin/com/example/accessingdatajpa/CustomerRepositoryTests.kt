/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.accessingdatajpa

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager

@DataJpaTest
class CustomerRepositoryTests @Autowired constructor(
    val entityManager: TestEntityManager,
    val customers: CustomerRepository
) {

    @Test
    fun testFindByLastName() {
        val customer = Customer("first", "last")
        entityManager.persist(customer)

        val findByLastName = customers.findByLastName(customer.lastName)

        assertThat(findByLastName).extracting<String> { it.lastName }.containsOnly(customer.lastName)
    }
}
