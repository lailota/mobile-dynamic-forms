//
//  FieldRow.swift
//  DynamicForms
//
//  Created by Laila Guzzon Hussein on 30/04/25.
//

import Foundation
import SwiftUI

// MARK: - Helpers

extension AttributedString {
    init?(html: String) {
        guard
            let data = html.data(using: .utf8),
            let mutable = try? NSMutableAttributedString(
                data: data,
                options: [
                    .documentType: NSAttributedString.DocumentType.html,
                    .characterEncoding: String.Encoding.utf8.rawValue
                ],
                documentAttributes: nil
            )
        else { return nil }
        self.init(mutable)
    }
}

struct HTMLText: View {
    let html: String

    var body: some View {
        // try using the above initializer
        if let attrStr = AttributedString(html: html) {
            Text(attrStr)
        } else {
            // fallback
            Text(html)
        }
    }
}

// MARK: - FieldRow

struct FieldRow: View {
    let field: Field
    @Binding var values: [String: String]

    var body: some View {
        VStack(alignment: .leading, spacing: 4) {
            Text(field.label)
                .font(.subheadline)
                .foregroundColor(.secondary)

            switch field.type {
            case "text", "number":
                TextField("", text: Binding(
                    get: { values[field.id] ?? "" },
                    set: { values[field.id] = $0 }
                ))
                .keyboardType(field.type == "number" ? .numberPad : .default)
                .textFieldStyle(RoundedBorderTextFieldStyle())

            case "dropdown":
                Picker(selection: Binding(
                    get: { values[field.id] ?? "" },
                    set: { values[field.id] = $0 }
                ), label: Text(values[field.id].flatMap { opt in
                    field.options?.first { $0.value == opt }?.label
                } ?? "Select")
                ) {
                    ForEach(field.options ?? [], id: \.value) { opt in
                        Text(opt.label).tag(opt.value)
                    }
                }
                .pickerStyle(MenuPickerStyle())

            case "date":
                DatePicker(
                    "",
                    selection: Binding(
                        get: {
                            if let s = values[field.id],
                               let date = Self.formatter.date(from: s) {
                                return date
                            }
                            return Date()
                        },
                        set: { values[field.id] = Self.formatter.string(from: $0) }
                    ),
                    displayedComponents: .date
                )
                .datePickerStyle(.compact)
                .labelsHidden()

            default:
                TextField("", text: Binding(
                    get: { values[field.id] ?? "" },
                    set: { values[field.id] = $0 }
                ))
                .textFieldStyle(RoundedBorderTextFieldStyle())
            }
        }
        .padding(.vertical, 8)
    }

    private static let formatter: DateFormatter = {
        let f = DateFormatter()
        f.dateFormat = "dd/MM/yyyy"
        return f
    }()
}
