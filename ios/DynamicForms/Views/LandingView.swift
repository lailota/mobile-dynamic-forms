//
//  LandingView.swift
//  DynamicForms
//
//  Created by Laila Guzzon Hussein on 30/04/25.
//
import SwiftUI

// Model representing an available “form”
struct FormInfo: Identifiable {
    var id: String { filename }
    let title: String
    let filename: String
}

struct LandingView: View {
    let availableForms = [
        FormInfo(title: "Complete Form", filename: "all-fields.json"),
        FormInfo(title: "Large Form (200 fields)", filename: "200-form.json")
    ]

    var body: some View {
        NavigationStack {
            List(availableForms) { form in
                NavigationLink {
                    let container = loadContainer(from: form.filename)
                    EntriesView(
                        jsonFilename: form.filename,
                        fields:    container.fields,
                        sections:  container.sections
                    )
                } label: {
                    Text(form.title)
                }
            }
            .navigationTitle("Forms")
        }
    }
}
