package com.jhkim.feature.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.jhkim.feature.detail.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.bookDetail.observe(viewLifecycleOwner) {
            binding.bookTitle.text = it.title
            binding.bookSubtitle.text = it.subtitle
            binding.bookRating.text = it.rating
            binding.bookRatingStar.rating = it.rating.toFloat()
            binding.bookImage.load(it.image)
            binding.bookPrice.text = it.price
            binding.bookDesc.text = it.desc
            binding.bookAuthors.text = it.authors
            binding.bookPublisher.text = it.publisher
            binding.bookYears.text = it.year
            binding.bookLanguage.text = it.language
            binding.bookPages.text = it.pages
            binding.bookIsbn10.text = it.isbn10
            binding.bookIsbn13.text = it.isbn13
            // TODO: link
        }
    }

}