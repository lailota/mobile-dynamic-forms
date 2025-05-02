//
//  FormView.swift
//  DynamicForms
//
//  Created by Laila Guzzon Hussein on 30/04/25.
//

import SwiftUI

struct FormView: View {
    let fields: [Field]
    let sections: [SectionModel]
    let onSubmit: ([String:String]) -> Void

    @State private var values: [String:String] = [:]
    @Environment(\.dismiss) private var dismiss

    // DateFormatter to converte Date ↔ String
    private let dateFormatter: DateFormatter = {
        let f = DateFormatter()
        f.dateFormat = "dd/MM/yyyy"
        return f
    }()

    var body: some View {
        NavigationStack {
            Form {
                ForEach(sections) { section in
                    Section(header: Text(section.title)) {
                        ForEach(fieldsIn(section)) { field in
                            fieldRow(field)
                        }
                    }
                }

                Section {
                    Button("Send") {
                        onSubmit(values)
                        dismiss()
                    }
                    .frame(maxWidth: .infinity, alignment: .center)
                }
            }
            .navigationTitle("Fill Form")
        }
    }

    // returns only fields within the section indexes
    private func fieldsIn(_ section: SectionModel) -> [Field] {
        Array(fields[section.from...section.to])
    }

    // generates the right View for each field type
    @ViewBuilder
    private func fieldRow(_ field: Field) -> some View {
        switch field.type {
        case "text", "number":
            TextField(
                field.label,
                text: binding(for: field.id)
            )
            .keyboardType(field.type == "number" ? .numberPad : .default)

        case "dropdown":
            Menu {
                // dropdown options
                ForEach(field.options ?? []) { opt in
                    Button(opt.label) {
                        values[field.id] = opt.value
                    }
                }
            } label: {
                HStack {
                    Text(field.label)
                    Spacer()
                    Text(values[field.id] ?? "Select")
                        .foregroundColor(.secondary)
                }
            }

        case "date":
            // DatePicker transform String into values
            let dateBinding = Binding<Date>(
                get: {
                    if let str = values[field.id], let d = dateFormatter.date(from: str) {
                        return d
                    }
                    return Date()
                },
                set: {
                    values[field.id] = dateFormatter.string(from: $0)
                }
            )
            DatePicker(field.label, selection: dateBinding, displayedComponents: .date)

        default:
            // any other type falls into a generic TextField
            TextField(
                field.label,
                text: binding(for: field.id)
            )
        }
    }

    // generic binding for any key of values
    private func binding(for key: String) -> Binding<String> {
        Binding<String>(
            get: { values[key, default: ""] },
            set: { values[key] = $0 }
        )
    }
}
