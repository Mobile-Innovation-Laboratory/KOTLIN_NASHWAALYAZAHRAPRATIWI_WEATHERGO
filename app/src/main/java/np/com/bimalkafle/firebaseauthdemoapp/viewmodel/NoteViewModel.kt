package np.com.bimalkafle.firebaseauthdemoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import np.com.bimalkafle.firebaseauthdemoapp.data.FirestoreRepository
import np.com.bimalkafle.firebaseauthdemoapp.model.Note
import java.util.UUID

class NoteViewModel(private val repository: FirestoreRepository) : ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes

    init {
        fetchNotes()
    }

    // 🔥 Ambil Catatan dari Firestore
    private fun fetchNotes() {
        viewModelScope.launch {
            try {
                _notes.value = repository.getNotes()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // 🔥 Tambah Catatan Baru
    fun addNote(title: String, content: String) {
        val newNote = Note(
            id = UUID.randomUUID().toString(), // Generate ID unik
            title = title,
            content = content,
            timestamp = Timestamp.now() // Gunakan Timestamp dari Firebase
        )

        viewModelScope.launch {
            try {
                repository.addNote(newNote)
                fetchNotes() // Refresh daftar catatan setelah tambah
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // 🔥 Update Catatan yang Sudah Ada
    fun updateNote(note: Note) {
        viewModelScope.launch {
            try {
                repository.updateNote(note)
                fetchNotes()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // 🔥 Hapus Catatan berdasarkan ID
    fun deleteNote(noteId: String) {
        viewModelScope.launch {
            try {
                repository.deleteNote(noteId)
                fetchNotes()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
