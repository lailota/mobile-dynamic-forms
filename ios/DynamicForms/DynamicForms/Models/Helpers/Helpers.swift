//
//  Helpers.swift
//  DynamicForms
//
//  Created by Laila Guzzon Hussein on 30/04/25.
//

import Foundation

struct FormContainer: Codable {
    let fields: [Field]
    let sections: [SectionModel]
}

func loadContainer(from filename: String)
-> (fields: [Field], sections: [SectionModel])
{
    let resource = filename.replacingOccurrences(of: ".json", with: "")
    guard let url = Bundle.main.url(
        forResource: resource, withExtension: "json")
    else {
        fatalError("Not found \(resource).json")
    }
    let data = try! Data(contentsOf: url)
    let container = try! JSONDecoder()
        .decode(FormContainer.self, from: data)
    return (container.fields, container.sections)
}
