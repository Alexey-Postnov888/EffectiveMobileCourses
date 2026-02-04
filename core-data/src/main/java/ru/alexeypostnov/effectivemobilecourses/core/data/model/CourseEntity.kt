package ru.alexeypostnov.effectivemobilecourses.core.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = CourseEntity.TABLE)
data class CourseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val text: String,
    val price: String,
    val rate: String,
    val startDate: String,
    val hasLike: Boolean,
    val publishDate: String
) {
    companion object {
        const val TABLE = "courses"
    }
}
