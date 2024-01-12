package com.jhkim.core.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jhkim.core.model.Book
import com.jhkim.feature.search.databinding.BookItemBinding

class SearchResultAdapter(
    private val onClick: (book: Book) -> Unit
) : ListAdapter<Book, BookHolder>(BookDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder = BookHolder(
        BookItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ),
        onClick
    )

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        val book = getItem(position) ?: return
        holder.bind(book)
    }

}

class BookHolder(
    private val binding: BookItemBinding,
    private val onClick: (book: Book) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(book: Book) {
        with(binding) {
            root.setOnClickListener { onClick(book) }
            bookTitle.text = book.title
            bookPrice.text = book.price
            bookIsbn.text = book.isbn13
            bookUrl.text = book.url
            bookImage.load(book.image) {
                crossfade(true)
            }

            if (book.subtitle.isBlank()) {
                bookSubtitle.visibility = View.GONE
            } else {
                bookSubtitle.visibility = View.VISIBLE
                bookSubtitle.text = book.subtitle
            }
        }
    }

}

object BookDiffCallback : DiffUtil.ItemCallback<Book>() {
    override fun areItemsTheSame(oldItem: Book, newItem: Book) = (oldItem == newItem)

    override fun areContentsTheSame(oldItem: Book, newItem: Book) =
        (oldItem.isbn13 == newItem.isbn13)

}