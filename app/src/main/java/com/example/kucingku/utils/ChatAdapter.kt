//import android.text.format.DateUtils
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.example.kucingku.R
//import com.example.kucingku.databinding.FragmentChatBinding
//import com.firebase.ui.database.FirebaseRecyclerAdapter
//import com.firebase.ui.database.FirebaseRecyclerOptions

class ChatAdapter{}
//    options: FirebaseRecyclerOptions<android.os.Message>,
//    private val currentUserName: String?
//) : FirebaseRecyclerAdapter<android.os.Message, ChatAdapter.MessageViewHolder>(options) {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        val view = inflater.inflate(R.layout.fragment_chat, parent, false)
//        val binding = FragmentChatBinding.bind(view)
//        return MessageViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(
//        holder: MessageViewHolder,
//        position: Int,
//        model: android.os.Message
//    ) {
//    }
//
//    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
//        holder.bind()
//    }
//
//
//    inner class MessageViewHolder(private val binding: FragmentChatBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(item: com.example.kucingku.data.Message) {
//            binding.tvMessage.text = item.text
//            setTextColor(item.name, binding.tvMessage)
//            binding.tvMessenger.text = item.name
//            Glide.with(itemView.context)
//                .load(item.photoUrl)
//                .circleCrop()
//                .into(binding.ivMessenger)
//            if (item.timestamp != null) {
//                binding.tvTimestamp.text = DateUtils.getRelativeTimeSpanString(item.timestamp)
//            }
//        }
//
//        private fun setTextColor(userName: String?, textView: TextView) {
//            if (currentUserName == userName && userName != null) {
//                textView.setBackgroundResource(R.drawable.rounded_message_blue)
//            } else {
//                textView.setBackgroundResource(R.drawable.rounded_message_yellow)
//            }
//        }
//
//        fun bind() {
//        }
//    }
//}
//
