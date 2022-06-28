package com.goofy.cache.domain

import com.goofy.cache.common.BaseEntity
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Board(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1,

    val title: String,

    val content: String,

    val type: Long
) : BaseEntity()
