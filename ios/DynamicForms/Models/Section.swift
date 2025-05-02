//
//  Section.swift
//  DynamicForms
//
//  Created by Laila Guzzon Hussein on 30/04/25.
//

import Foundation

struct SectionModel: Codable, Identifiable {
    let id: String
    let title: String
    let from: Int
    let to: Int

    enum CodingKeys: String, CodingKey {
        case id = "uuid", title, from, to
    }
}
