package uk.ac.tees.w9640628.uniclubs.data

import uk.ac.tees.w9640628.uniclubs.R

class ClubData {
    fun loadClubs(): List<Club>{
        return listOf<Club>(
            Club(
                id = "1",
                name = "Art Club",
                description = "For people who can redefine the world with art. Join us.",
                imageResId = R.drawable.artclub
            ),
            Club(
                id = "2",
                name = "Coding Club",
                description = "Bring your ideas to life.",
                imageResId = R.drawable.codingclub
            ),
            Club(
                id = "3",
                name = "Football Club",
                description = "A place for all the football lovers.",
                imageResId = R.drawable.footballclub
            ),
            Club(
                id = "4",
                name = "Photography Club",
                description = "Capturing Moments, Creating Memories: Where Every Click Tells a Story.",
                imageResId = R.drawable.photographyclub
            )

            )
    }
}