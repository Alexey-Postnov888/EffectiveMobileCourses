package ru.alexeypostnov.effectivemobilecourses.core.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.alexeypostnov.effectivemobilecourses.core.data.model.CourseEntity

@Dao
interface CoursesDAO {
    @Upsert
    suspend fun upsertCourse(course: CourseEntity)

    @Upsert
    suspend fun upsertCourses(courses: List<CourseEntity>)

    @Query("SELECT * FROM ${CourseEntity.TABLE} ORDER BY startDate")
    fun getCourses(): Flow<List<CourseEntity>>

    @Query("SELECT * FROM ${CourseEntity.TABLE} WHERE id = :courseId")
    fun getCourseById(courseId: Int): Flow<CourseEntity?>

    @Query("SELECT * FROM ${CourseEntity.TABLE} WHERE hasLike = 1 ORDER BY startDate")
    fun getFavouritesCourses(): Flow<List<CourseEntity>>

    @Query("UPDATE ${CourseEntity.TABLE} SET hasLike = :hasLike WHERE id = :courseId")
    suspend fun updateFavouriteStatusByCourseId(courseId: Int, hasLike: Boolean)
}