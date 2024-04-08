//
//  TimerScreen.swift
//  ios
//
//  Created by Rajapaksage Isuru Rajapakse on 8/4/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import counter

class TimerViewModel: ObservableObject {
  @Published var seconds:Int = 0
  
  let domain = TimerDomain()
  
  init() {
    domain.onState { [self] seconds in self.seconds = seconds.intValue }
  }
}


struct TimerScreen: View {
  @ObservedObject var viewModel = TimerViewModel()

  var body: some View {
    TimerView(seconds: viewModel.seconds)
  }
}

struct TimerView: View {
  let seconds: Int
  
  var body: some View {
    Text("\(seconds)s")
      .font(.largeTitle)
  }
}

#Preview {
  TimerView(seconds: 42)
}
