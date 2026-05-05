//
//  HTKLinePriceLine.swift
//  HTKLineView
//
//  Created by Codex on 2026/01/28.
//

import UIKit

struct HTKLinePriceLine {
    let id: String
    let value: CGFloat
    let color: UIColor
    let width: CGFloat
    let dash: [CGFloat]
    let label: String
    let labelPosition: String
    let labelColor: UIColor
    let visible: Bool

    static func packModelArray(
        _ modelList: [[String: Any]],
        defaultColor: UIColor,
        defaultLabelColor: UIColor,
        defaultWidth: CGFloat
    ) -> [HTKLinePriceLine] {
        var lines = [HTKLinePriceLine]()
        for dictionary in modelList {
            let id = dictionary["id"] as? String ?? ""
            let rawValue = dictionary["price"] ?? dictionary["value"]
            guard let number = rawValue as? NSNumber else {
                continue
            }
            let value = CGFloat(truncating: number)
            let colorValue = dictionary["color"] as? Int
            let color = (colorValue != nil ? RCTConvert.uiColor(colorValue) : nil) ?? defaultColor
            let widthValue = dictionary["width"] as? NSNumber
            let width = widthValue != nil ? CGFloat(truncating: widthValue!) : defaultWidth
            let dashList = dictionary["dash"] as? [NSNumber] ?? []
            let dash = dashList.map { CGFloat(truncating: $0) }
            let label = dictionary["label"] as? String ?? ""
            let labelPosition = dictionary["labelPosition"] as? String ?? "right"
            let labelColorValue = dictionary["labelColor"] as? Int
            let labelColor = (labelColorValue != nil ? RCTConvert.uiColor(labelColorValue) : nil) ?? defaultLabelColor
            let visible = dictionary["visible"] as? Bool ?? true

            lines.append(
                HTKLinePriceLine(
                    id: id,
                    value: value,
                    color: color,
                    width: width,
                    dash: dash,
                    label: label,
                    labelPosition: labelPosition,
                    labelColor: labelColor,
                    visible: visible
                )
            )
        }
        return lines
    }
}
