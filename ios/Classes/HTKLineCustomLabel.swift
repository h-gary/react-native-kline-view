//
//  HTKLineCustomLabel.swift
//  HTKLineView
//
//  Created by Codex on 2026/05/04.
//

import UIKit

class HTKLineCustomLabel: NSObject {

    var time = ""
    var label = ""
    var color: UIColor = .clear

    static func packModelArray(_ modelList: [[String: Any]], defaultColor: UIColor) -> [HTKLineCustomLabel] {
        var modelArray = [HTKLineCustomLabel]()
        for dictionary in modelList {
            let time = dictionary["time"] as? String ?? dictionary["dateString"] as? String ?? ""
            let label = dictionary["label"] as? String ?? ""
            if time.isEmpty || label.isEmpty {
                continue
            }
            let itemModel = HTKLineCustomLabel()
            itemModel.time = time
            itemModel.label = label
            itemModel.color = RCTConvert.uiColor(dictionary["color"]) ?? defaultColor
            modelArray.append(itemModel)
        }
        return modelArray
    }
}
