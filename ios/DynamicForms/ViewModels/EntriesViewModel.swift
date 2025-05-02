//
//  EntriesViewModel.swift
//  DynamicForms
//
//  Created by Laila Guzzon Hussein on 30/04/25.
//

import Foundation
import Combine

struct FormEntry: Identifiable {
    let id = UUID()
    let jsonData: [String:String]
    let timestamp: Date
}

final class EntriesViewModel: ObservableObject {
    @Published var entries: [FormEntry] = []
    private let jsonFilename: String

    init(jsonFilename: String) {
        self.jsonFilename = jsonFilename
        load()
    }

    private func load() {
        let key = "entries-\(jsonFilename)"
        let raw = UserDefaults.standard.array(forKey: key) as? [[String:String]] ?? []
        self.entries = raw.map { FormEntry(jsonData: $0, timestamp: Date()) }
    }

    func add(_ values: [String:String]) {
        save(values)
        load()
    }

    private func save(_ values: [String:String]) {
        let key = "entries-\(jsonFilename)"
        var all = UserDefaults.standard.array(forKey: key) as? [[String:String]] ?? []
        all.append(values)
        UserDefaults.standard.set(all, forKey: key)
    }
}
