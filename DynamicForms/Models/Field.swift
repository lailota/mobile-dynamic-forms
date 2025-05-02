//
//  Field.swift
//  DynamicForms
//
//  Created by Laila Guzzon Hussein on 30/04/25.
//

import Foundation

struct Field: Codable, Identifiable {
    let id: String
    let type: String
    let label: String
    let options: [Option]?

    enum CodingKeys: String, CodingKey {
        case id = "uuid"
        case type, label, options
    }
}

struct Option: Codable, Identifiable {
    let id = UUID()
    let label: String
    let value: String
}
