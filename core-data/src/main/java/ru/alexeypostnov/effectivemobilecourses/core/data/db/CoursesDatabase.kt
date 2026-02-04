package ru.alexeypostnov.effectivemobilecourses.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.alexeypostnov.effectivemobilecourses.core.data.model.CourseEntity

@Database(
    entities = [
        CourseEntity::class
    ],
    version = 1,
    autoMigrations = []
)
abstract class CoursesDatabase: RoomDatabase() {
    abstract val coursesDAO: CoursesDAO
}