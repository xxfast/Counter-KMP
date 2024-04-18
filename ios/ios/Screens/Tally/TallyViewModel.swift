//
//  TallyViewModel.swift
//  ios
//
//  Created by Rajapaksage Isuru Rajapakse on 18/4/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import counter

class TallyViewModel: ObservableObject {
  @Published var state:TallyState = TallyState(thousands: 0, hundreds: 0, tens: 0, ones: 0)
  
  let domain = TallyDomain()
  
  init() {
    domain.collect { state in
      self.state = state
    }
  }
  
  func onIncrease() { domain.onIncrease() }
  func onReset() { domain.onReset() }
}
