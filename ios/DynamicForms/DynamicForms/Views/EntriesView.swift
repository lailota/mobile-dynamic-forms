//
//  EntriesView.swift
//  DynamicForms
//
//  Created by Laila Guzzon Hussein on 30/04/25.
//

import SwiftUI

struct EntriesView: View {
    @StateObject private var vm: EntriesViewModel
    let fields:   [Field]
    let sections: [SectionModel]

    init(jsonFilename: String,
         fields: [Field],
         sections: [SectionModel])
    {
        _vm = StateObject(
            wrappedValue: EntriesViewModel(jsonFilename: jsonFilename)
        )
        self.fields   = fields
        self.sections = sections
    }

    var body: some View {
        List {
            // 1) existing entries
            ForEach(vm.entries) { entry in
                VStack(alignment: .leading) {
                    Text("Enviado em \(entry.timestamp, format: .dateTime.year().month().day().hour().minute())")
                        .font(.footnote)
                        .foregroundColor(.secondary)
                    // example: show first name
                    Text(entry.jsonData["first_name"] ?? "—")
                        .font(.body)
                }
                .padding(.vertical, 4)
            }

            // 2) "New Submission" section
            Section {
                NavigationLink {
                    FormView(
                        fields:   fields,
                        sections: sections
                    ) { newData in
                        // callback de submit: insert directly into the list
                        vm.entries.insert(
                            FormEntry(
                                jsonData:  newData,
                                timestamp: Date()
                            ),
                            at: 0
                        )
                    }
                } label: {
                    Label("New Submission", systemImage: "plus.circle")
                        .font(.body)
                }
            }
        }
        .navigationTitle("Submissions")
        .listStyle(.insetGrouped)
    }
}
