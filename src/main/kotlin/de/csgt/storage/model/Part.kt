package de.csgt.storage.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*
import kotlin.collections.ArrayList

@Entity
@EntityListeners(AuditingEntityListener::class)
data class Part(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        @CreatedDate
        var createdAt: Date? = null,

        @LastModifiedDate
        var lastModified: Date? = null,

        var name: String = "",

        @OneToMany(mappedBy = "part")
        val buys: List<Buy> = ArrayList(),

        @OneToMany(mappedBy = "part", fetch = FetchType.EAGER)
        val storages: MutableList<Storage> = ArrayList(),

        @OneToMany(mappedBy = "part")
        val sellParts: List<SellPart> = ArrayList()) {

    override fun toString(): String {
        return "Part(id=$id, createdAt=$createdAt, lastModified=$lastModified, name='$name')"
    }
}