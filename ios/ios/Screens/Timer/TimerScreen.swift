//
//  TimerScreen.swift
//  ios
//
//  Created by Rajapaksage Isuru Rajapakse on 8/4/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct TimerScreen: View {
  @ObservedObject var viewModel = TimerViewModel()

  var body: some View {
    TimerView(
      minutes: viewModel.minutes,
      seconds: viewModel.seconds
    )
  }
}

struct TimerView: View {
  let minutes: Int
  let seconds: Int
  
  var body: some View {
    Text("\(minutes)m \(seconds)s")
      .font(.largeTitle)
  }
}

#Preview {
  TimerView(minutes:9, seconds: 42)
}
