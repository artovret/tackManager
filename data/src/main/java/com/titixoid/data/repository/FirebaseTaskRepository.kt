package com.titixoid.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.titixoid.data.models.TaskDao
import com.titixoid.data.models.toDomainTask
import com.titixoid.domain.models.Task
import com.titixoid.domain.repository.TaskRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseTaskRepository(
    private val firestore: FirebaseFirestore
) : TaskRepository {

    override fun getTasksForWorkerFlow(workerId: String): Flow<List<Task>> = callbackFlow {
        val query = firestore.collection("tasks")
            .whereEqualTo("workerId", workerId)
            .whereEqualTo("completed", false)

        val listener = query.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            val tasks = snapshot?.documents?.mapNotNull { doc ->
                doc.toObject(TaskDao::class.java)?.toDomainTask(doc.id)
            } ?: emptyList()

            trySend(tasks).isSuccess
        }

        awaitClose { listener.remove() }
    }

    override suspend fun getAllTasks(): List<Task> {
        val snapshot = firestore.collection("tasks")
            .get()
            .await()

        return snapshot.documents.mapNotNull { doc ->
            doc.toObject(TaskDao::class.java)?.toDomainTask(doc.id)
        }
    }

    override suspend fun createTask(task: Task): Boolean {
        val taskMap = hashMapOf(
            "title" to task.title,
            "description" to task.description,
            "status" to task.status,
            "completed" to task.completed,
            "workerId" to task.workerId
        )
        return try {
            firestore.collection("tasks").add(taskMap).await()
            true
        } catch (e: Exception) {
            false
        }
    }
}