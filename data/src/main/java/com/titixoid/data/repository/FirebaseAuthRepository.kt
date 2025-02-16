package com.titixoid.data.repository

//class FirebaseAuthRepository @Inject constructor(
//    private val auth: FirebaseAuth,
//    private val firestore: FirebaseFirestore
//) : AuthRepository {
//
//    override suspend fun signIn(email: String, password: String): Resource<User> {
//        return try {
//            val result = auth.signInWithEmailAndPassword(email, password).await()
//            val user = result.user?.toDomainUser(firestore)
//            Resource.Success(user ?: throw Exception("User not found"))
//        } catch (e: Exception) {
//            Resource.Error(e)
//        }
//    }
//
//    override suspend fun signUp(email: String, password: String, role: UserRole): Resource<User> {
//        return try {
//            val result = auth.createUserWithEmailAndPassword(email, password).await()
//            val user = result.user ?: throw Exception("Registration failed")
//
//            // Сохраняем роль в Firestore
//            firestore.collection("users").document(user.uid).set(
//                hashMapOf(
//                    "id" to user.uid,
//                    "email" to email,
//                    "role" to role.name
//                )
//            ).await()
//
//            Resource.Success(user.toDomainUser(firestore))
//        } catch (e: Exception) {
//            Resource.Error(e)
//        }
//    }
//}