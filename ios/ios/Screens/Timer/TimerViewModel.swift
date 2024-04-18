//
//  TimerViewModel.swift
//  ios
//
//  Created by Rajapaksage Isuru Rajapakse on 18/4/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import counter

class TimerViewModel: ObservableObject {
  @Published var minutes:Int = 0
  @Published var seconds:Int = 0
  
  let domain = TimerDomain()
  
  init() {
    domain.collect { state in
      self.minutes = Int(state.minutes)
      self.seconds = Int(state.seconds)
    }
  }
}
