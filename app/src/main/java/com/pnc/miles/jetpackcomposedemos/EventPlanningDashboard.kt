package com.pnc.miles.jetpackcomposedemos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EventPlanningDashboard() {
    val artist = Artist(
        id = 100,
        name = "Selfie and the SimChips",
        genre = "Pop",
        location = "Miami, FL",
        imageUrl = "/images/selfiesim.jpg",
        description = "Some description of this pop group...",
        tags = "Pop,Music,Modern,Dance"
    )
    val member = BoardMember(
        id= 101,
        firstName= "Morgan",
        lastName= "Sloan",
        title= "CEO",
        gender= "F",
        photo= "MorganSloan.jpg",
        imageUrl= "/images/MorganSloan.jpg",
        bio= "<h3>Education</h3><ul><li>Received a B.A. in Rambling from Faber College</li><li>Earned a M.A. in Dithering from Whatsamatter U.</li><li>Earned a Ph.D. in Bloviating from Pendleton State University</li></ul><h3>History/Accomplishments</h3><p>Won several awards for consulting about electric trains in Atlantic City, NJ. Have a strong interest in getting to know junk food in Mexico. Enthusiastic about developing strategies for bacon for farmers. Spent the better part of the 90's implementing soap scum in Suffolk, NY. Spent 2001-2005 consulting about Uno in the aftermarket. Spent 2002-2008 researching methane in Orlando, FL.</p>"
    )

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Event Planning Dashboard",
            style = MaterialTheme.typography.headlineMedium
        )
//        ArtistCard(artist = artist)
        BoardMemberCard(member = member)
    }
}

