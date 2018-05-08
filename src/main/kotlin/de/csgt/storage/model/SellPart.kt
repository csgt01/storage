package de.csgt.storage.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
data class SellPart (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        @CreatedDate
        var createdAt: Date? = null,

        @LastModifiedDate
        var lastModified: Date? = null,

        var quantity: Int,

        var price: Double? = null,

        @ManyToOne
        @JoinColumn(name="part_id")
        var part: Part? = null,

        @ManyToOne
        @JoinColumn(name="sell_id")
        var sell: Sell) {
        override fun toString(): String {
                return "SellPart(id=$id, createdAt=$createdAt, lastModified=$lastModified, quantity=$quantity, price=$price)"
        }
}