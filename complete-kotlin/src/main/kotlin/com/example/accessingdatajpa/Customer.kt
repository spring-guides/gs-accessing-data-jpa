package com.example.accessingdatajpa

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Customer(
    var firstName: String = "",
    var lastName: String = ""
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    override fun toString(): String =
        "Customer[id=$id, firstName='$firstName', lastName='$lastName']"
}