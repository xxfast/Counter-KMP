//
//  TallyScreen.swift
//  ios
//
//  Created by Rajapaksage Isuru Rajapakse on 18/4/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import counter

struct TallyScreen: View {
  @ObservedObject var viewModel = TallyViewModel()

  var body: some View {
    TallyView(
      state: viewModel.state,
      onIncrease: { viewModel.onIncrease() },
      onReset: { viewModel.onReset() }
    )
    .animation(.default)
  }
}

struct TallyView: View {
  let state: TallyState
  let onIncrease: () -> Void
  let onReset: () -> Void
  
  var body: some View {
    Button(
      action: { withAnimation { onIncrease() } }
    ) {
      HStack {
        Text("\(state.thousands)\(state.hundreds)\(state.tens)\(state.ones)")
          .contentTransition(.numericText())
          .font(.largeTitle)
        
        Spacer()
        
        Button (
          action: onReset,
          label: {
            Image(systemName: "arrow.counterclockwise")
          }
        )
      }
    }
  }
}

#Preview {
  TallyView(
    state: TallyState(thousands: 3, hundreds: 4, tens: 3, ones: 5),
    onIncrease: { },
    onReset: { }
  )
}
