package de.csgt.storage.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Size

@Entity
@EntityListeners(AuditingEntityListener::class)
data class Buy (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        @CreatedDate
        var createdAt: Date? = null,

        @LastModifiedDate
        var lastModified: Date? = null,

        var price: Double,

        var quantity: Int,

        @ManyToOne
        @JoinColumn(name="part_id")
        var part: Part? = null,

        @ManyToOne
        @JoinColumn(name="bill_id")
        var bill: Bill)