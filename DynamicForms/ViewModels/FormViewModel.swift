//
//  FormViewModel.swift
//  DynamicForms
//
//  Created by Laila Guzzon Hussein on 30/04/25.
//

import Foundation

final class FormViewModel: ObservableObject {
    @Published var fields: [Field] = []
    @Published var sections: [SectionModel] = []

    private let jsonFilename: String

    init(jsonFilename: String) {
        self.jsonFilename = jsonFilename

        // Destructures the returned tuple
        let (loadedFields, loadedSections) = loadContainer(from: jsonFilename)
        self.fields = loadedFields
        self.sections = loadedSections
    }

    func saveEntry(_ values: [String:String]) {
        let key = "entries-\(jsonFilename)"
        var all = UserDefaults.standard.array(forKey: key) as? [[String:String]] ?? []
        all.append(values)
        UserDefaults.standard.set(all, forKey: key)
    }
}
