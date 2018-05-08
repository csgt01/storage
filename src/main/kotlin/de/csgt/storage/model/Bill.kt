package de.csgt.storage.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotEmpty
import kotlin.collections.ArrayList

@Entity
@EntityListeners(AuditingEntityListener::class)
data class Bill (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        @CreatedDate
        var createdAt: Date? = null,

        @LastModifiedDate
        var lastModified: Date? = null,

        @field:NotEmpty
        var shop: String? = null,
        var payed: LocalDateTime? = null,

        @OneToMany(mappedBy = "bill", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
        val buys: MutableList<Buy> = ArrayList()) {

        override fun toString(): String {
                return "Bill(id=$id, createdAt=$createdAt, lastModified=$lastModified, payed=$payed)"
        }

        override fun equals(other: Any?): Boolean {
                other as Bill
                return other.id == this.id
        }

//        override fun hashCode(): Int {
//                return id!!.hashCode()
//        }
}