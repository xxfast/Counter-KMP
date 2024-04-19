//
//  FactsViewModel.swift
//  ios
//
//  Created by Rajapaksage Isuru Rajapakse on 20/4/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import counter

class FactsViewModel: ObservableObject {
  @Published var state:FactsState = FactsState.Loading.shared
  
  let domain = FactsDomain()
  
  init() {
    domain.collect { state in
      self.state = state
    }
  }
  
  func onRefresh() { domain.onRefresh() }
}

