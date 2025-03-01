package np.com.bimalkafle.firebaseauthdemoapp.model



import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName

data class Note(
    val id: String = "",
    val title: String = "",
    val content: String = "",
    @PropertyName("timestamp") val timestamp: Timestamp? = null // Biarkan sebagai Timestamp
) {
    fun getTimestampAsLong(): Long {
        return timestamp?.toDate()?.time ?: 0L
    }
}

