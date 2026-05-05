//
//  HTKKlineModel.swift
//  HTKLineView
//
//  Created by hublot on 2020/3/18.
//  Copyright © 2020 hublot. All rights reserved.
//

import UIKit

class HTKLineItemModel: NSObject {

    var value: CGFloat = 0

    var title = ""

    var selected = true

    var index = 0

    static func packModelArray(_ modelList: [[String: Any]]) -> [HTKLineItemModel] {
        var modelArray = [HTKLineItemModel]()
        for dictionary in modelList {
            let itemModel = HTKLineItemModel()
            itemModel.title = dictionary["title"] as? String ?? ""
            itemModel.value = dictionary["value"] as? CGFloat ?? 0
            itemModel.selected = dictionary["selected"] as? Bool ?? true
            itemModel.index = dictionary["index"] as? Int ?? 0
            if itemModel.selected {
                modelArray.append(itemModel)
            }
        }
        return modelArray
    }

}

class HTKLineReferenceLine: NSObject {

    static let labelPositionLeft = "left"
    static let labelPositionRight = "right"

    var id = ""
    var key = ""
    var value: CGFloat = 0
    var color: UIColor?
    var width: CGFloat?
    var dash: [CGFloat]?
    var label: String?
    var labelPosition: String = HTKLineReferenceLine.labelPositionRight
    var labelColor: UIColor?
    var visible: Bool = true

    static func packModelArray(_ modelList: [[String: Any]]) -> [HTKLineReferenceLine] {
        var modelArray = [HTKLineReferenceLine]()
        for dictionary in modelList {
            let priceValue = dictionary["price"] as? CGFloat
            let valueValue = dictionary["value"] as? CGFloat
            guard let value = priceValue ?? valueValue else {
                continue
            }
            let itemModel = HTKLineReferenceLine()
            itemModel.value = value
            itemModel.id = dictionary["id"] as? String ?? ""
            itemModel.key = dictionary["key"] as? String ?? ""
            itemModel.color = RCTConvert.uiColor(dictionary["color"])
            if let width = dictionary["width"] as? CGFloat {
                itemModel.width = width
            }
            if let dash = dictionary["dash"] as? [NSNumber], dash.count > 0 {
                itemModel.dash = dash.map { CGFloat(truncating: $0) }
            } else if let dash = dictionary["dash"] as? [CGFloat], dash.count > 0 {
                itemModel.dash = dash
            }
            itemModel.label = dictionary["label"] as? String
            if let labelPosition = dictionary["labelPosition"] as? String {
                itemModel.labelPosition = labelPosition
            }
            itemModel.labelColor = RCTConvert.uiColor(dictionary["labelColor"])
            if let visible = dictionary["visible"] as? Bool {
                itemModel.visible = visible
            }
            modelArray.append(itemModel)
        }
        return modelArray
    }
}


class HTKLineModel: NSObject {

    var dateString: String = ""

    var id: CGFloat = 0

    var open: CGFloat = 0

    var high: CGFloat = 0

    var low: CGFloat = 0

    var close: CGFloat = 0

    var volume: CGFloat = 0

    var bollMb: CGFloat = 0

    var bollUp: CGFloat = 0

    var bollDn: CGFloat = 0

    var maList = [HTKLineItemModel]()

    var maVolumeList = [HTKLineItemModel]()

    var macdValue: CGFloat = 0

    var macdDea: CGFloat = 0

    var macdDif: CGFloat = 0

    var kdjK: CGFloat = 0

    var kdjD: CGFloat = 0

    var kdjJ: CGFloat = 0

    var rsiList = [HTKLineItemModel]()

    var wrList = [HTKLineItemModel]()

    var selectedItemList = [[String: Any]]()

    lazy var increment: Bool = {
        let increment = close >= open
        return increment
    }()

    static func packModel(_ dictionary: [String: Any]) -> HTKLineModel {
        let model = HTKLineModel()
        model.id = dictionary["id"] as? CGFloat ?? 0
        model.dateString = dictionary["dateString"] as? String ?? ""
        model.open = dictionary["open"] as? CGFloat ?? 0
        model.high = dictionary["high"] as? CGFloat ?? 0
        model.low = dictionary["low"] as? CGFloat ?? 0
        model.close = dictionary["close"] as? CGFloat ?? 0
        model.volume = dictionary["vol"] as? CGFloat ?? 0
        model.maList = HTKLineItemModel.packModelArray(dictionary["maList"] as? [[String: Any]] ?? [])
        model.maVolumeList = HTKLineItemModel.packModelArray(dictionary["maVolumeList"] as? [[String: Any]] ?? [])
        model.rsiList = HTKLineItemModel.packModelArray(dictionary["rsiList"] as? [[String: Any]] ?? [])
        model.wrList = HTKLineItemModel.packModelArray(dictionary["wrList"] as? [[String: Any]] ?? [])
        model.bollMb = dictionary["bollMb"] as? CGFloat ?? 0
        model.bollUp = dictionary["bollUp"] as? CGFloat ?? 0
        model.bollDn = dictionary["bollDn"] as? CGFloat ?? 0
        model.macdValue = dictionary["macdValue"] as? CGFloat ?? 0
        model.macdDea = dictionary["macdDea"] as? CGFloat ?? 0
        model.macdDif = dictionary["macdDif"] as? CGFloat ?? 0
        model.kdjK = dictionary["kdjK"] as? CGFloat ?? 0
        model.kdjD = dictionary["kdjD"] as? CGFloat ?? 0
        model.kdjJ = dictionary["kdjJ"] as? CGFloat ?? 0
        var selectedItemList = dictionary["selectedItemList"] as? [[String: Any]] ?? [[String: Any]]()
        for (i, dictionary) in selectedItemList.enumerated() {
            if let color = dictionary["color"] as? Int {
                selectedItemList[i]["color"] = RCTConvert.uiColor(color)
            }
        }
        model.selectedItemList = selectedItemList
        return model
    }

    static func packModelArray(_ modelList: [[String: Any]]) -> [HTKLineModel] {
        var modelArray = [HTKLineModel]()

        for dictionary in modelList {
            let model = packModel(dictionary)
            modelArray.append(model)
        }
        return modelArray
    }

}
