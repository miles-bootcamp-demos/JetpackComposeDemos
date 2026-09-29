package com.pnc.miles.jetpackcomposedemos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun BoardMemberList(
    members: List<BoardMember>,
    onMemberSelected: (Int) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        members.forEach { member ->
            BoardMemberCard(
                member = member,
                onClick = {
                    onMemberSelected(member.id)
                }
            )
        }
    }
}