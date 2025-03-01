package np.com.bimalkafle.firebaseauthdemoapp.data


import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

import kotlinx.coroutines.tasks.await
import np.com.bimalkafle.firebaseauthdemoapp.model.Note

class FirestoreRepository {
    private val db = FirebaseFirestore.getInstance()
    private val notesCollection = db.collection("notes")

    // 🔥 Tambah Catatan ke Firestore
    suspend fun addNote(note: Note): String? {
        val documentRef = notesCollection.add(note).await()
        return documentRef.id
    }

    // 🔥 Ambil Semua Catatan dari Firestore
    suspend fun getNotes(): List<Note> {
        val snapshot = notesCollection.get().await()
        return snapshot.documents.mapNotNull { it.toObject<Note>()?.copy(id = it.id) }
    }

    // 🔥 Update Catatan di Firestore
    suspend fun updateNote(note: Note) {
        notesCollection.document(note.id).set(note).await()
    }

    // 🔥 Hapus Catatan dari Firestore
    suspend fun deleteNote(noteId: String) {
        notesCollection.document(noteId).delete().await()
    }
}
