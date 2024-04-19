//
//  FactsScreen.swift
//  ios
//
//  Created by Rajapaksage Isuru Rajapakse on 20/4/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import counter

struct FactsScreen: View {
  @ObservedObject var viewModel = FactsViewModel()

  var body: some View {
    FactsView(
      state: viewModel.state,
      onRefresh: { viewModel.onRefresh() }
    )
  }
}

struct FactsView: View {
  let state: FactsState
  let onRefresh: () -> Void
  
  var body: some View {
    Group {
      switch(state){
      case FactsState.Loading.shared:
        ProgressView()
      case FactsState.Failed.shared:
        Image(systemName: "arrow.counterclockwise")
          .frame(width: 48, height: 48)
      case let succss as FactsState.Success:
        Text(succss.fact)
          .font(.title2)
        
      // You have to return some view in SwiftUI - can't just throw errors here
      default: EmptyView()
      }
    }
    .onTapGesture { onRefresh() }
  }
}

#Preview {
  FactsView(
    state: FactsState.Loading.shared,
    onRefresh: {}
  )
}
