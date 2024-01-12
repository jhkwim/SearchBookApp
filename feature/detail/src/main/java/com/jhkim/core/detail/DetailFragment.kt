package com.jhkim.core.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.jhkim.feature.detail.R
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

        viewModel.detailState.observe(viewLifecycleOwner) { state ->

            when(state) {
                DetailState.Error -> {
                    Toast.makeText(requireContext(), R.string.fail_to_get_detail, Toast.LENGTH_SHORT).show()
                }
                DetailState.None -> {}
                is DetailState.Success -> {
                    with(state.bookDetail) {
                        binding.bookTitle.text = title
                        binding.bookSubtitle.text = subtitle
                        binding.bookRating.text = rating
                        binding.bookRatingStar.rating = rating.toFloat()
                        binding.bookImage.load(image)
                        binding.bookPrice.text = price
                        binding.bookDesc.text = desc
                        binding.bookAuthors.text = authors
                        binding.bookPublisher.text = publisher
                        binding.bookYears.text = year
                        binding.bookLanguage.text = language
                        binding.bookPages.text = pages
                        binding.bookIsbn10.text = isbn10
                        binding.bookIsbn13.text = isbn13
                        binding.bookUrl.text = url
                    }

                }
            }


        }
    }

}