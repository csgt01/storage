package de.csgt.storage.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.text.DecimalFormat
import java.util.*
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
data class Sell (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        @CreatedDate
        var createdAt: Date? = null,

        @LastModifiedDate
        var lastModified: Date? = null,

        @OneToMany(mappedBy = "sell", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
        val sellParts: MutableList<SellPart> = ArrayList()) {

        @Transient
        @JsonIgnore
        var totalCosts: Double? = null
                get() {
                    return sellParts.sumByDouble { it -> it.price!! }
                }

}