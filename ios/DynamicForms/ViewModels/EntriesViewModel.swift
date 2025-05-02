//
//  EntriesViewModel.swift
//  DynamicForms
//
//  Created by Laila Guzzon Hussein on 30/04/25.
//

import Foundation
import Combine

struct FormEntry: Identifiable, Codable {
    var id = UUID()
    let jsonData: [String:String]
    let timestamp: Date

    init(id: UUID = .init(), jsonData: [String:String], timestamp: Date = .init()) {
        self.id = id
        self.jsonData = jsonData
        self.timestamp = timestamp
    }
}

final class EntriesViewModel: ObservableObject {
    @Published var entries: [FormEntry] = []
    private let key: String

    init(jsonFilename: String) {
        self.key = "entries-\(jsonFilename)"
        load()
    }

    private func load() {
        guard
            let data = UserDefaults.standard.data(forKey: key),
            let decoded = try? JSONDecoder().decode([FormEntry].self, from: data)
        else {
            entries = []
            return
        }
        // order from newest to oldest
        entries = decoded.sorted { $0.timestamp > $1.timestamp }
    }

    func add(jsonData: [String:String]) {
        let new = FormEntry(jsonData: jsonData)
        entries.insert(new, at: 0)
        save()
    }

    private func save() {
        if let encoded = try? JSONEncoder().encode(entries) {
            UserDefaults.standard.set(encoded, forKey: key)
        }
    }
}
