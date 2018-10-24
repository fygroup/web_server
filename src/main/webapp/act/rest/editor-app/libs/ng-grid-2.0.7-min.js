(function (e, t) {
    "use strict";
    var n = 6, o = 4, i = "asc", r = "desc", l = "_ng_field_", a = "_ng_depth_", s = "_ng_hidden_", c = "_ng_column_",
        g = /CUSTOM_FILTERS/g, d = /COL_FIELD/g, u = /DISPLAY_CELL_TEMPLATE/g, f = /EDITABLE_CELL_TEMPLATE/g,
        h = /<.+>/;
    e.ngGrid = {}, e.ngGrid.i18n = {}, angular.module("ngGrid.services", []);
    var p = angular.module("ngGrid.directives", []), m = angular.module("ngGrid.filters", []);
    angular.module("ngGrid", ["ngGrid.services", "ngGrid.directives", "ngGrid.filters"]);
    var v = function (e, t, o, i) {
        if (void 0 === e.selectionProvider.selectedItems) return !0;
        var r, l = o.which || o.keyCode, a = !1, s = !1,
            c = void 0 === e.selectionProvider.lastClickedRow ? 1 : e.selectionProvider.lastClickedRow.rowIndex,
            g = e.columns.filter(function (e) {
                return e.visible
            }), d = e.columns.filter(function (e) {
                return e.pinned
            });
        if (e.col && (r = g.indexOf(e.col)), 37 !== l && 38 !== l && 39 !== l && 40 !== l && 9 !== l && 13 !== l) return !0;
        if (e.enableCellSelection) {
            9 === l && o.preventDefault();
            var u = e.showSelectionCheckbox ? 1 === e.col.index : 0 === e.col.index,
                f = 1 === e.$index || 0 === e.$index,
                h = e.$index === e.renderedColumns.length - 1 || e.$index === e.renderedColumns.length - 2,
                p = g.indexOf(e.col) === g.length - 1, m = d.indexOf(e.col) === d.length - 1;
            if (37 === l || 9 === l && o.shiftKey) {
                var v = 0;
                u || (r -= 1), f ? u && 9 === l && o.shiftKey ? (v = i.$canvas.width(), r = g.length - 1, s = !0) : v = i.$viewport.scrollLeft() - e.col.width : d.length > 0 && (v = i.$viewport.scrollLeft() - g[r].width), i.$viewport.scrollLeft(v)
            } else (39 === l || 9 === l && !o.shiftKey) && (h ? p && 9 === l && !o.shiftKey ? (i.$viewport.scrollLeft(0), r = e.showSelectionCheckbox ? 1 : 0, a = !0) : i.$viewport.scrollLeft(i.$viewport.scrollLeft() + e.col.width) : m && i.$viewport.scrollLeft(0), p || (r += 1))
        }
        var w;
        w = e.configGroups.length > 0 ? i.rowFactory.parsedData.filter(function (e) {
            return !e.isAggRow
        }) : i.filteredRows;
        var C = 0;
        if (0 !== c && (38 === l || 13 === l && o.shiftKey || 9 === l && o.shiftKey && s) ? C = -1 : c !== w.length - 1 && (40 === l || 13 === l && !o.shiftKey || 9 === l && a) && (C = 1), C) {
            var b = w[c + C];
            b.beforeSelectionChange(b, o) && (b.continueSelection(o), e.$emit("ngGridEventDigestGridParent"), e.selectionProvider.lastClickedRow.renderedRowIndex >= e.renderedRows.length - n - 2 ? i.$viewport.scrollTop(i.$viewport.scrollTop() + e.rowHeight) : n + 2 >= e.selectionProvider.lastClickedRow.renderedRowIndex && i.$viewport.scrollTop(i.$viewport.scrollTop() - e.rowHeight))
        }
        return e.enableCellSelection && setTimeout(function () {
            e.domAccessProvider.focusCellElement(e, e.renderedColumns.indexOf(g[r]))
        }, 3), !1
    };
    String.prototype.trim || (String.prototype.trim = function () {
        return this.replace(/^\s+|\s+$/g, "")
    }), Array.prototype.indexOf || (Array.prototype.indexOf = function (e) {
        var t = this.length >>> 0, n = Number(arguments[1]) || 0;
        for (n = 0 > n ? Math.ceil(n) : Math.floor(n), 0 > n && (n += t); t > n; n++) if (n in this && this[n] === e) return n;
        return -1
    }), Array.prototype.filter || (Array.prototype.filter = function (e) {
        var t = Object(this), n = t.length >>> 0;
        if ("function" != typeof e) throw new TypeError;
        for (var o = [], i = arguments[1], r = 0; n > r; r++) if (r in t) {
            var l = t[r];
            e.call(i, l, r, t) && o.push(l)
        }
        return o
    }), m.filter("checkmark", function () {
        return function (e) {
            return e ? "✔" : "✘"
        }
    }), m.filter("ngColumns", function () {
        return function (e) {
            return e.filter(function (e) {
                return !e.isAggCol
            })
        }
    }), angular.module("ngGrid.services").factory("$domUtilityService", ["$utilityService", function (e) {
        var n = {}, o = {}, i = function () {
            var e = t("<div></div>");
            e.appendTo("body"), e.height(100).width(100).css("position", "absolute").css("overflow", "scroll"), e.append('<div style="height: 400px; width: 400px;"></div>'), n.ScrollH = e.height() - e[0].clientHeight, n.ScrollW = e.width() - e[0].clientWidth, e.empty(), e.attr("style", ""), e.append('<span style="font-family: Verdana, Helvetica, Sans-Serif; font-size: 14px;"><strong>M</strong></span>'), n.LetterW = e.children().first().width(), e.remove()
        };
        return n.eventStorage = {}, n.AssignGridContainers = function (e, o, i) {
            i.$root = t(o), i.$topPanel = i.$root.find(".ngTopPanel"), i.$groupPanel = i.$root.find(".ngGroupPanel"), i.$headerContainer = i.$topPanel.find(".ngHeaderContainer"), e.$headerContainer = i.$headerContainer, i.$headerScroller = i.$topPanel.find(".ngHeaderScroller"), i.$headers = i.$headerScroller.children(), i.$viewport = i.$root.find(".ngViewport"), i.$canvas = i.$viewport.find(".ngCanvas"), i.$footerPanel = i.$root.find(".ngFooterPanel"), e.$watch(function () {
                return i.$viewport.scrollLeft()
            }, function (e) {
                return i.$headerContainer.scrollLeft(e)
            }), n.UpdateGridLayout(e, i)
        }, n.getRealWidth = function (e) {
            var n = 0, o = {visibility: "hidden", display: "block"}, i = e.parents().andSelf().not(":visible");
            return t.swap(i[0], o, function () {
                n = e.outerWidth()
            }), n
        }, n.UpdateGridLayout = function (e, t) {
            var o = t.$viewport.scrollTop();
            t.elementDims.rootMaxW = t.$root.width(), t.$root.is(":hidden") && (t.elementDims.rootMaxW = n.getRealWidth(t.$root)), t.elementDims.rootMaxH = t.$root.height(), t.refreshDomSizes(), e.adjustScrollTop(o, !0)
        }, n.numberOfGrids = 0, n.BuildStyles = function (o, i, r) {
            var l, a = i.config.rowHeight, s = i.$styleSheet, c = i.gridId, g = o.columns, d = 0;
            s || (s = t("#" + c), s[0] || (s = t("<style id='" + c + "' type='text/css' rel='stylesheet' />").appendTo(i.$root))), s.empty();
            var u = o.totalRowWidth();
            l = "." + c + " .ngCanvas { width: " + u + "px; }" + "." + c + " .ngRow { width: " + u + "px; }" + "." + c + " .ngCanvas { width: " + u + "px; }" + "." + c + " .ngHeaderScroller { width: " + (u + n.ScrollH) + "px}";
            for (var f = 0; g.length > f; f++) {
                var h = g[f];
                h.visible !== !1 && (l += "." + c + " .col" + f + " { width: " + h.width + "px; left: " + d + "px; height: " + a + "px }" + "." + c + " .colt" + f + " { width: " + h.width + "px; }", d += h.width)
            }
            e.isIe ? s[0].styleSheet.cssText = l : s[0].appendChild(document.createTextNode(l)), i.$styleSheet = s, o.adjustScrollLeft(i.$viewport.scrollLeft()), r && n.digest(o)
        }, n.setColLeft = function (t, n, i) {
            if (i.$styleSheet) {
                var r = o[t.index];
                r || (r = o[t.index] = RegExp(".col" + t.index + " { width: [0-9]+px; left: [0-9]+px"));
                var l = i.$styleSheet.html(),
                    a = l.replace(r, ".col" + t.index + " { width: " + t.width + "px; left: " + n + "px");
                e.isIe ? setTimeout(function () {
                    i.$styleSheet.html(a)
                }) : i.$styleSheet.html(a)
            }
        }, n.setColLeft.immediate = 1, n.RebuildGrid = function (e, t) {
            n.UpdateGridLayout(e, t), (null == t.config.maintainColumnRatios || t.config.maintainColumnRatios) && t.configureColumnWidths(), e.adjustScrollLeft(t.$viewport.scrollLeft()), n.BuildStyles(e, t, !0)
        }, n.digest = function (e) {
            e.$root.$$phase || e.$digest()
        }, n.ScrollH = 17, n.ScrollW = 17, n.LetterW = 10, i(), n
    }]), angular.module("ngGrid.services").factory("$sortService", ["$parse", function (e) {
        var t = {};
        return t.colSortFnCache = {}, t.guessSortFn = function (e) {
            var n = typeof e;
            switch (n) {
                case"number":
                    return t.sortNumber;
                case"boolean":
                    return t.sortBool;
                case"string":
                    return e.match(/^[-+]?[£$¤]?[\d,.]+%?$/) ? t.sortNumberStr : t.sortAlpha;
                default:
                    return "[object Date]" === Object.prototype.toString.call(e) ? t.sortDate : t.basicSort
            }
        }, t.basicSort = function (e, t) {
            return e === t ? 0 : t > e ? -1 : 1
        }, t.sortNumber = function (e, t) {
            return e - t
        }, t.sortNumberStr = function (e, t) {
            var n, o, i = !1, r = !1;
            return n = parseFloat(e.replace(/[^0-9.-]/g, "")), isNaN(n) && (i = !0), o = parseFloat(t.replace(/[^0-9.-]/g, "")), isNaN(o) && (r = !0), i && r ? 0 : i ? 1 : r ? -1 : n - o
        }, t.sortAlpha = function (e, t) {
            var n = e.toLowerCase(), o = t.toLowerCase();
            return n === o ? 0 : o > n ? -1 : 1
        }, t.sortDate = function (e, t) {
            var n = e.getTime(), o = t.getTime();
            return n === o ? 0 : o > n ? -1 : 1
        }, t.sortBool = function (e, t) {
            return e && t ? 0 : e || t ? e ? 1 : -1 : 0
        }, t.sortData = function (n, o) {
            if (o && n) {
                var r, l, a = n.fields.length, s = n.fields, c = o.slice(0);
                o.sort(function (o, g) {
                    for (var d, u = 0, f = 0; 0 === u && a > f;) {
                        r = n.columns[f], l = n.directions[f], d = t.getSortFn(r, c);
                        var h = e(s[f])(o), p = e(s[f])(g);
                        !h && 0 !== h || !p && 0 !== p ? p || h ? h ? p || (u = -1) : u = 1 : u = 0 : u = d(h, p), f++
                    }
                    return l === i ? u : 0 - u
                })
            }
        }, t.Sort = function (e, n) {
            t.isSorting || (t.isSorting = !0, t.sortData(e, n), t.isSorting = !1)
        }, t.getSortFn = function (n, o) {
            var i, r;
            if (t.colSortFnCache[n.field]) i = t.colSortFnCache[n.field]; else if (void 0 !== n.sortingAlgorithm) i = n.sortingAlgorithm, t.colSortFnCache[n.field] = n.sortingAlgorithm; else {
                if (r = o[0], !r) return i;
                i = t.guessSortFn(e(n.field)(r)), i ? t.colSortFnCache[n.field] = i : i = t.sortAlpha
            }
            return i
        }, t
    }]), angular.module("ngGrid.services").factory("$utilityService", ["$parse", function (n) {
        var o = /function (.{1,})\(/, i = {
            visualLength: function (e) {
                var n = document.getElementById("testDataLength");
                return n || (n = document.createElement("SPAN"), n.id = "testDataLength", n.style.visibility = "hidden", document.body.appendChild(n)), t(n).css("font", t(e).css("font")), t(n).css("font-size", t(e).css("font-size")), t(n).css("font-family", t(e).css("font-family")), n.innerHTML = t(e).text(), n.offsetWidth
            }, forIn: function (e, t) {
                for (var n in e) e.hasOwnProperty(n) && t(e[n], n)
            }, evalProperty: function (e, t) {
                return n(t)(e)
            }, endsWith: function (e, t) {
                return e && t && "string" == typeof e ? -1 !== e.indexOf(t, e.length - t.length) : !1
            }, isNullOrUndefined: function (e) {
                return void 0 === e || null === e ? !0 : !1
            }, getElementsByClassName: function (e) {
                for (var t = [], n = RegExp("\\b" + e + "\\b"), o = document.getElementsByTagName("*"), i = 0; o.length > i; i++) {
                    var r = o[i].className;
                    n.test(r) && t.push(o[i])
                }
                return t
            }, newId: function () {
                var e = (new Date).getTime();
                return function () {
                    return e += 1
                }
            }(), seti18n: function (t, n) {
                var o = e.ngGrid.i18n[n];
                for (var i in o) t.i18n[i] = o[i]
            }, getInstanceType: function (e) {
                var t = o.exec("" + e.constructor);
                if (t && t.length > 1) {
                    var n = t[1].replace(/^\s+|\s+$/g, "");
                    return n
                }
                return ""
            }, ieVersion: function () {
                var e = 3, t = document.createElement("div"), n = t.getElementsByTagName("i");
                do t.innerHTML = "<!--[if gt IE " + ++e + "]><i></i><![endif]-->"; while (n[0]);
                return e > 4 ? e : void 0
            }()
        };
        return t.extend(i, {
            isIe: function () {
                return void 0 !== i.ieVersion
            }()
        }), i
    }]);
    var w = function (e, t, n, o) {
        this.rowIndex = 0, this.offsetTop = this.rowIndex * n, this.entity = e, this.label = e.gLabel, this.field = e.gField, this.depth = e.gDepth, this.parent = e.parent, this.children = e.children, this.aggChildren = e.aggChildren, this.aggIndex = e.aggIndex, this.collapsed = o, this.groupInitState = o, this.rowFactory = t, this.rowHeight = n, this.isAggRow = !0, this.offsetLeft = 25 * e.gDepth, this.aggLabelFilter = e.aggLabelFilter
    };
    w.prototype.toggleExpand = function () {
        this.collapsed = this.collapsed ? !1 : !0, this.orig && (this.orig.collapsed = this.collapsed), this.notifyChildren()
    }, w.prototype.setExpand = function (e) {
        this.collapsed = e, this.notifyChildren()
    }, w.prototype.notifyChildren = function () {
        for (var e = Math.max(this.rowFactory.aggCache.length, this.children.length), t = 0; e > t; t++) if (this.aggChildren[t] && (this.aggChildren[t].entity[s] = this.collapsed, this.collapsed && this.aggChildren[t].setExpand(this.collapsed)), this.children[t] && (this.children[t][s] = this.collapsed), t > this.aggIndex && this.rowFactory.aggCache[t]) {
            var n = this.rowFactory.aggCache[t], o = 30 * this.children.length;
            n.offsetTop = this.collapsed ? n.offsetTop - o : n.offsetTop + o
        }
        this.rowFactory.renderedChange()
    }, w.prototype.aggClass = function () {
        return this.collapsed ? "ngAggArrowCollapsed" : "ngAggArrowExpanded"
    }, w.prototype.totalChildren = function () {
        if (this.aggChildren.length > 0) {
            var e = 0, t = function (n) {
                n.aggChildren.length > 0 ? angular.forEach(n.aggChildren, function (e) {
                    t(e)
                }) : e += n.children.length
            };
            return t(this), e
        }
        return this.children.length
    }, w.prototype.copy = function () {
        var e = new w(this.entity, this.rowFactory, this.rowHeight, this.groupInitState);
        return e.orig = this, e
    };
    var C = function (e, n, o, l, a, s) {
        var c = this, d = e.colDef, u = 500, f = 0, p = null;
        c.colDef = e.colDef, c.width = d.width, c.groupIndex = 0, c.isGroupedBy = !1, c.minWidth = d.minWidth ? d.minWidth : 50, c.maxWidth = d.maxWidth ? d.maxWidth : 9e3, c.enableCellEdit = void 0 !== d.enableCellEdit ? d.enableCellEdit : e.enableCellEdit || e.enableCellEditOnFocus, c.headerRowHeight = e.headerRowHeight, c.displayName = void 0 === d.displayName ? d.field : d.displayName, c.index = e.index, c.isAggCol = e.isAggCol, c.cellClass = d.cellClass, c.sortPriority = void 0, c.cellFilter = d.cellFilter ? d.cellFilter : "", c.field = d.field, c.aggLabelFilter = d.cellFilter || d.aggLabelFilter, c.visible = s.isNullOrUndefined(d.visible) || d.visible, c.sortable = !1, c.resizable = !1, c.pinnable = !1, c.pinned = e.enablePinning && d.pinned, c.originalIndex = null == e.originalIndex ? c.index : e.originalIndex, c.groupable = s.isNullOrUndefined(d.groupable) || d.groupable, e.enableSort && (c.sortable = s.isNullOrUndefined(d.sortable) || d.sortable), e.enableResize && (c.resizable = s.isNullOrUndefined(d.resizable) || d.resizable), e.enablePinning && (c.pinnable = s.isNullOrUndefined(d.pinnable) || d.pinnable), c.sortDirection = void 0, c.sortingAlgorithm = d.sortFn, c.headerClass = d.headerClass, c.cursor = c.sortable ? "pointer" : "default", c.headerCellTemplate = d.headerCellTemplate || a.get("headerCellTemplate.html"), c.cellTemplate = d.cellTemplate || a.get("cellTemplate.html").replace(g, c.cellFilter ? "|" + c.cellFilter : ""), c.enableCellEdit && (c.cellEditTemplate = a.get("cellEditTemplate.html"), c.editableCellTemplate = d.editableCellTemplate || a.get("editableCellTemplate.html")), d.cellTemplate && !h.test(d.cellTemplate) && (c.cellTemplate = t.ajax({
            type: "GET",
            url: d.cellTemplate,
            async: !1
        }).responseText), c.enableCellEdit && d.editableCellTemplate && !h.test(d.editableCellTemplate) && (c.editableCellTemplate = t.ajax({
            type: "GET",
            url: d.editableCellTemplate,
            async: !1
        }).responseText), d.headerCellTemplate && !h.test(d.headerCellTemplate) && (c.headerCellTemplate = t.ajax({
            type: "GET",
            url: d.headerCellTemplate,
            async: !1
        }).responseText), c.colIndex = function () {
            var e = c.pinned ? "pinned " : "";
            return e += "col" + c.index + " colt" + c.index, c.cellClass && (e += " " + c.cellClass), e
        }, c.groupedByClass = function () {
            return c.isGroupedBy ? "ngGroupedByIcon" : "ngGroupIcon"
        }, c.toggleVisible = function () {
            c.visible = !c.visible
        }, c.showSortButtonUp = function () {
            return c.sortable ? c.sortDirection === r : c.sortable
        }, c.showSortButtonDown = function () {
            return c.sortable ? c.sortDirection === i : c.sortable
        }, c.noSortVisible = function () {
            return !c.sortDirection
        }, c.sort = function (t) {
            if (!c.sortable) return !0;
            var n = c.sortDirection === i ? r : i;
            return c.sortDirection = n, e.sortCallback(c, t), !1
        }, c.gripClick = function () {
            f++, 1 === f ? p = setTimeout(function () {
                f = 0
            }, u) : (clearTimeout(p), e.resizeOnDataCallback(c), f = 0)
        }, c.gripOnMouseDown = function (e) {
            return n.isColumnResizing = !0, e.ctrlKey && !c.pinned ? (c.toggleVisible(), l.BuildStyles(n, o), !0) : (e.target.parentElement.style.cursor = "col-resize", c.startMousePosition = e.clientX, c.origWidth = c.width, t(document).mousemove(c.onMouseMove), t(document).mouseup(c.gripOnMouseUp), !1)
        }, c.onMouseMove = function (e) {
            var t = e.clientX - c.startMousePosition, i = t + c.origWidth;
            return c.width = c.minWidth > i ? c.minWidth : i > c.maxWidth ? c.maxWidth : i, n.hasUserChangedGridColumnWidths = !0, l.BuildStyles(n, o), !1
        }, c.gripOnMouseUp = function (e) {
            return t(document).off("mousemove", c.onMouseMove), t(document).off("mouseup", c.gripOnMouseUp), e.target.parentElement.style.cursor = "default", l.digest(n), n.isColumnResizing = !1, !1
        }, c.copy = function () {
            var t = new C(e, n, o, l, a);
            return t.isClone = !0, t.orig = c, t
        }, c.setVars = function (e) {
            c.orig = e, c.width = e.width, c.groupIndex = e.groupIndex, c.isGroupedBy = e.isGroupedBy, c.displayName = e.displayName, c.index = e.index, c.isAggCol = e.isAggCol, c.cellClass = e.cellClass, c.cellFilter = e.cellFilter, c.field = e.field, c.aggLabelFilter = e.aggLabelFilter, c.visible = e.visible, c.sortable = e.sortable, c.resizable = e.resizable, c.pinnable = e.pinnable, c.pinned = e.pinned, c.originalIndex = e.originalIndex, c.sortDirection = e.sortDirection, c.sortingAlgorithm = e.sortingAlgorithm, c.headerClass = e.headerClass, c.headerCellTemplate = e.headerCellTemplate, c.cellTemplate = e.cellTemplate, c.cellEditTemplate = e.cellEditTemplate
        }
    }, b = function (e) {
        this.outerHeight = null, this.outerWidth = null, t.extend(this, e)
    }, S = function (e) {
        this.previousColumn = null, this.grid = e
    };
    S.prototype.changeUserSelect = function (e, t) {
        e.css({
            "-webkit-touch-callout": t,
            "-webkit-user-select": t,
            "-khtml-user-select": t,
            "-moz-user-select": "none" === t ? "-moz-none" : t,
            "-ms-user-select": t,
            "user-select": t
        })
    }, S.prototype.focusCellElement = function (e, t) {
        if (e.selectionProvider.lastClickedRow) {
            var n = void 0 !== t ? t : this.previousColumn,
                o = e.selectionProvider.lastClickedRow.clone ? e.selectionProvider.lastClickedRow.clone.elm : e.selectionProvider.lastClickedRow.elm;
            if (void 0 !== n && o) {
                var i = angular.element(o[0].children).filter(function () {
                    return 8 !== this.nodeType
                }), r = Math.max(Math.min(e.renderedColumns.length - 1, n), 0);
                this.grid.config.showSelectionCheckbox && angular.element(i[r]).scope() && 0 === angular.element(i[r]).scope().col.index && (r = 1), i[r] && i[r].children[1].children[0].focus(), this.previousColumn = n
            }
        }
    }, S.prototype.selectionHandlers = function (e, t) {
        var n = !1, o = this;
        t.bind("keydown", function (i) {
            if (16 === i.keyCode) return o.changeUserSelect(t, "none", i), !0;
            if (!n) {
                n = !0;
                var r = v(e, t, i, o.grid);
                return n = !1, r
            }
            return !0
        }), t.bind("keyup", function (e) {
            return 16 === e.keyCode && o.changeUserSelect(t, "text", e), !0
        })
    };
    var x = function (n, o, i, r) {
        var l = this;
        l.colToMove = void 0, l.groupToMove = void 0, l.assignEvents = function () {
            n.config.jqueryUIDraggable && !n.config.enablePinning ? n.$groupPanel.droppable({
                addClasses: !1,
                drop: function (e) {
                    l.onGroupDrop(e)
                }
            }) : (n.$groupPanel.on("mousedown", l.onGroupMouseDown).on("dragover", l.dragOver).on("drop", l.onGroupDrop), n.$headerScroller.on("mousedown", l.onHeaderMouseDown).on("dragover", l.dragOver), n.config.enableColumnReordering && !n.config.enablePinning && n.$headerScroller.on("drop", l.onHeaderDrop)), o.$watch("renderedColumns", function () {
                r(l.setDraggables)
            })
        }, l.dragStart = function (e) {
            e.dataTransfer.setData("text", "")
        }, l.dragOver = function (e) {
            e.preventDefault()
        }, l.setDraggables = function () {
            if (n.config.jqueryUIDraggable) n.$root.find(".ngHeaderSortColumn").draggable({
                helper: "clone",
                appendTo: "body",
                stack: "div",
                addClasses: !1,
                start: function (e) {
                    l.onHeaderMouseDown(e)
                }
            }).droppable({
                drop: function (e) {
                    l.onHeaderDrop(e)
                }
            }); else {
                var e = n.$root.find(".ngHeaderSortColumn");
                angular.forEach(e, function (e) {
                    e.className && -1 !== e.className.indexOf("ngHeaderSortColumn") && (e.setAttribute("draggable", "true"), e.addEventListener && e.addEventListener("dragstart", l.dragStart))
                }), -1 !== navigator.userAgent.indexOf("MSIE") && n.$root.find(".ngHeaderSortColumn").bind("selectstart", function () {
                    return this.dragDrop(), !1
                })
            }
        }, l.onGroupMouseDown = function (e) {
            var o = t(e.target);
            if ("ngRemoveGroup" !== o[0].className) {
                var i = angular.element(o).scope();
                i && (n.config.jqueryUIDraggable || (o.attr("draggable", "true"), this.addEventListener && this.addEventListener("dragstart", l.dragStart), -1 !== navigator.userAgent.indexOf("MSIE") && o.bind("selectstart", function () {
                    return this.dragDrop(), !1
                })), l.groupToMove = {header: o, groupName: i.group, index: i.$index})
            } else l.groupToMove = void 0
        }, l.onGroupDrop = function (e) {
            e.stopPropagation();
            var i, r;
            l.groupToMove ? (i = t(e.target).closest(".ngGroupElement"), "ngGroupPanel" === i.context.className ? (o.configGroups.splice(l.groupToMove.index, 1), o.configGroups.push(l.groupToMove.groupName)) : (r = angular.element(i).scope(), r && l.groupToMove.index !== r.$index && (o.configGroups.splice(l.groupToMove.index, 1), o.configGroups.splice(r.$index, 0, l.groupToMove.groupName))), l.groupToMove = void 0, n.fixGroupIndexes()) : l.colToMove && (-1 === o.configGroups.indexOf(l.colToMove.col) && (i = t(e.target).closest(".ngGroupElement"), "ngGroupPanel" === i.context.className || "ngGroupPanelDescription ng-binding" === i.context.className ? o.groupBy(l.colToMove.col) : (r = angular.element(i).scope(), r && o.removeGroup(r.$index))), l.colToMove = void 0), o.$$phase || o.$apply()
        }, l.onHeaderMouseDown = function (e) {
            var n = t(e.target).closest(".ngHeaderSortColumn"), o = angular.element(n).scope();
            o && (l.colToMove = {header: n, col: o.col})
        }, l.onHeaderDrop = function (e) {
            if (l.colToMove && !l.colToMove.col.pinned) {
                var r = t(e.target).closest(".ngHeaderSortColumn"), a = angular.element(r).scope();
                if (a) {
                    if (l.colToMove.col === a.col) return;
                    o.columns.splice(l.colToMove.col.index, 1), o.columns.splice(a.col.index, 0, l.colToMove.col), n.fixColumnIndexes(), l.colToMove = void 0, i.digest(o)
                }
            }
        }, l.assignGridEventHandlers = function () {
            -1 === n.config.tabIndex ? (n.$viewport.attr("tabIndex", i.numberOfGrids), i.numberOfGrids++) : n.$viewport.attr("tabIndex", n.config.tabIndex);
            var r;
            t(e).resize(function () {
                clearTimeout(r), r = setTimeout(function () {
                    i.RebuildGrid(o, n)
                }, 100)
            });
            var l;
            t(n.$root.parent()).on("resize", function () {
                clearTimeout(l), l = setTimeout(function () {
                    i.RebuildGrid(o, n)
                }, 100)
            })
        }, l.assignGridEventHandlers(), l.assignEvents()
    }, y = function (e, t) {
        e.maxRows = function () {
            var n = Math.max(e.totalServerItems, t.data.length);
            return n
        }, e.multiSelect = t.config.enableRowSelection && t.config.multiSelect, e.selectedItemCount = t.selectedItemCount, e.maxPages = function () {
            return Math.ceil(e.maxRows() / e.pagingOptions.pageSize)
        }, e.pageForward = function () {
            var t = e.pagingOptions.currentPage;
            e.totalServerItems > 0 ? e.pagingOptions.currentPage = Math.min(t + 1, e.maxPages()) : e.pagingOptions.currentPage++
        }, e.pageBackward = function () {
            var t = e.pagingOptions.currentPage;
            e.pagingOptions.currentPage = Math.max(t - 1, 1)
        }, e.pageToFirst = function () {
            e.pagingOptions.currentPage = 1
        }, e.pageToLast = function () {
            var t = e.maxPages();
            e.pagingOptions.currentPage = t
        }, e.cantPageForward = function () {
            var n = e.pagingOptions.currentPage, o = e.maxPages();
            return e.totalServerItems > 0 ? n >= o : 1 > t.data.length
        }, e.cantPageToLast = function () {
            return e.totalServerItems > 0 ? e.cantPageForward() : !0
        }, e.cantPageBackward = function () {
            var t = e.pagingOptions.currentPage;
            return 1 >= t
        }
    }, T = function (i, r, l, a, c, g, d, u, f, p, m) {
        var v = {
            aggregateTemplate: void 0,
            afterSelectionChange: function () {
            },
            beforeSelectionChange: function () {
                return !0
            },
            checkboxCellTemplate: void 0,
            checkboxHeaderTemplate: void 0,
            columnDefs: void 0,
            data: [],
            dataUpdated: function () {
            },
            enableCellEdit: !1,
            enableCellEditOnFocus: !1,
            enableCellSelection: !1,
            enableColumnResize: !1,
            enableColumnReordering: !1,
            enableColumnHeavyVirt: !1,
            enablePaging: !1,
            enablePinning: !1,
            enableRowSelection: !0,
            enableSorting: !0,
            enableHighlighting: !1,
            excludeProperties: [],
            filterOptions: {filterText: "", useExternalFilter: !1},
            footerRowHeight: 55,
            footerTemplate: void 0,
            groups: [],
            groupsCollapsedByDefault: !0,
            headerRowHeight: 30,
            headerRowTemplate: void 0,
            jqueryUIDraggable: !1,
            jqueryUITheme: !1,
            keepLastSelected: !0,
            maintainColumnRatios: void 0,
            menuTemplate: void 0,
            multiSelect: !0,
            pagingOptions: {pageSizes: [250, 500, 1e3], pageSize: 250, currentPage: 1},
            pinSelectionCheckbox: !1,
            plugins: [],
            primaryKey: void 0,
            rowHeight: 30,
            rowTemplate: void 0,
            selectedItems: [],
            selectWithCheckboxOnly: !1,
            showColumnMenu: !1,
            showFilter: !1,
            showFooter: !1,
            showGroupPanel: !1,
            showSelectionCheckbox: !1,
            sortInfo: {fields: [], columns: [], directions: []},
            tabIndex: -1,
            totalServerItems: 0,
            useExternalSorting: !1,
            i18n: "en",
            virtualizationThreshold: 50
        }, w = this;
        w.maxCanvasHt = 0, w.config = t.extend(v, e.ngGrid.config, r), w.config.showSelectionCheckbox = w.config.showSelectionCheckbox && w.config.enableColumnHeavyVirt === !1, w.config.enablePinning = w.config.enablePinning && w.config.enableColumnHeavyVirt === !1, w.config.selectWithCheckboxOnly = w.config.selectWithCheckboxOnly && w.config.showSelectionCheckbox !== !1, w.config.pinSelectionCheckbox = w.config.enablePinning, "string" == typeof r.columnDefs && (w.config.columnDefs = i.$eval(r.columnDefs)), w.rowCache = [], w.rowMap = [], w.gridId = "ng" + d.newId(), w.$root = null, w.$groupPanel = null, w.$topPanel = null, w.$headerContainer = null, w.$headerScroller = null, w.$headers = null, w.$viewport = null, w.$canvas = null, w.rootDim = w.config.gridDim, w.data = [], w.lateBindColumns = !1, w.filteredRows = [], w.initTemplates = function () {
            var e = ["rowTemplate", "aggregateTemplate", "headerRowTemplate", "checkboxCellTemplate", "checkboxHeaderTemplate", "menuTemplate", "footerTemplate"],
                t = [];
            return angular.forEach(e, function (e) {
                t.push(w.getTemplate(e))
            }), m.all(t)
        }, w.getTemplate = function (e) {
            var t = w.config[e], n = w.gridId + e + ".html", o = m.defer();
            if (t && !h.test(t)) p.get(t, {cache: g}).success(function (e) {
                g.put(n, e), o.resolve()
            }).error(function () {
                o.reject("Could not load template: " + t)
            }); else if (t) g.put(n, t), o.resolve(); else {
                var i = e + ".html";
                g.put(n, g.get(i)), o.resolve()
            }
            return o.promise
        }, "object" == typeof w.config.data && (w.data = w.config.data), w.calcMaxCanvasHeight = function () {
            var e;
            return e = w.config.groups.length > 0 ? w.rowFactory.parsedData.filter(function (e) {
                return !e[s]
            }).length * w.config.rowHeight : w.filteredRows.length * w.config.rowHeight
        }, w.elementDims = {
            scrollW: 0,
            scrollH: 0,
            rowIndexCellW: 25,
            rowSelectedCellW: 25,
            rootMaxW: 0,
            rootMaxH: 0
        }, w.setRenderedRows = function (e) {
            i.renderedRows.length = e.length;
            for (var t = 0; e.length > t; t++) !i.renderedRows[t] || e[t].isAggRow || i.renderedRows[t].isAggRow ? (i.renderedRows[t] = e[t].copy(), i.renderedRows[t].collapsed = e[t].collapsed, e[t].isAggRow || i.renderedRows[t].setVars(e[t])) : i.renderedRows[t].setVars(e[t]), i.renderedRows[t].rowIndex = e[t].rowIndex, i.renderedRows[t].offsetTop = e[t].offsetTop, i.renderedRows[t].selected = e[t].selected, e[t].renderedRowIndex = t;
            w.refreshDomSizes(), i.$emit("ngGridEventRows", e)
        }, w.minRowsToRender = function () {
            var e = i.viewportDimHeight() || 1;
            return Math.floor(e / w.config.rowHeight)
        }, w.refreshDomSizes = function () {
            var e = new b;
            e.outerWidth = w.elementDims.rootMaxW, e.outerHeight = w.elementDims.rootMaxH, w.rootDim = e, w.maxCanvasHt = w.calcMaxCanvasHeight()
        }, w.buildColumnDefsFromData = function () {
            w.config.columnDefs = [];
            var e = w.data[0];
            return e ? (d.forIn(e, function (e, t) {
                -1 === w.config.excludeProperties.indexOf(t) && w.config.columnDefs.push({field: t})
            }), void 0) : (w.lateBoundColumns = !0, void 0)
        }, w.buildColumns = function () {
            var e = w.config.columnDefs, t = [];
            if (e || (w.buildColumnDefsFromData(), e = w.config.columnDefs), w.config.showSelectionCheckbox && t.push(new C({
                colDef: {
                    field: "✔",
                    width: w.elementDims.rowSelectedCellW,
                    sortable: !1,
                    resizable: !1,
                    groupable: !1,
                    headerCellTemplate: g.get(i.gridId + "checkboxHeaderTemplate.html"),
                    cellTemplate: g.get(i.gridId + "checkboxCellTemplate.html"),
                    pinned: w.config.pinSelectionCheckbox
                },
                index: 0,
                headerRowHeight: w.config.headerRowHeight,
                sortCallback: w.sortData,
                resizeOnDataCallback: w.resizeOnData,
                enableResize: w.config.enableColumnResize,
                enableSort: w.config.enableSorting,
                enablePinning: w.config.enablePinning
            }, i, w, a, g, d)), e.length > 0) {
                var n = w.config.showSelectionCheckbox ? 1 : 0, o = i.configGroups.length;
                i.configGroups.length = 0, angular.forEach(e, function (e, r) {
                    r += n;
                    var l = new C({
                        colDef: e,
                        index: r + o,
                        originalIndex: r,
                        headerRowHeight: w.config.headerRowHeight,
                        sortCallback: w.sortData,
                        resizeOnDataCallback: w.resizeOnData,
                        enableResize: w.config.enableColumnResize,
                        enableSort: w.config.enableSorting,
                        enablePinning: w.config.enablePinning,
                        enableCellEdit: w.config.enableCellEdit || w.config.enableCellEditOnFocus
                    }, i, w, a, g, d), s = w.config.groups.indexOf(e.field);
                    -1 !== s && (l.isGroupedBy = !0, i.configGroups.splice(s, 0, l), l.groupIndex = i.configGroups.length), t.push(l)
                }), i.columns = t, w.config.groups.length > 0 && w.rowFactory.getGrouping(w.config.groups)
            }
        }, w.configureColumnWidths = function () {
            var e = [], t = [], n = 0, o = 0, r = {};
            if (angular.forEach(i.columns, function (e, t) {
                if (!d.isNullOrUndefined(e.originalIndex)) {
                    var n = e.originalIndex;
                    w.config.showSelectionCheckbox && (0 === e.originalIndex && e.visible && (o += 25), n--), r[n] = t
                }
            }), angular.forEach(w.config.columnDefs, function (l, a) {
                var s = i.columns[r[a]];
                l.index = a;
                var c, g = !1;
                if (d.isNullOrUndefined(l.width) ? l.width = "*" : (g = isNaN(l.width) ? d.endsWith(l.width, "%") : !1, c = g ? l.width : parseInt(l.width, 10)), isNaN(c) && !i.hasUserChangedGridColumnWidths) {
                    if (c = l.width, "auto" === c) {
                        s.width = s.minWidth, o += s.width;
                        var u = s;
                        return i.$on("ngGridEventData", function () {
                            w.resizeOnData(u)
                        }), void 0
                    }
                    if (-1 !== c.indexOf("*")) return s.visible !== !1 && (n += c.length), e.push(l), void 0;
                    if (g) return t.push(l), void 0;
                    throw'unable to parse column width, use percentage ("10%","20%", etc...) or "*" to use remaining width of grid'
                }
                s.visible !== !1 && (o += s.width = parseInt(s.width, 10))
            }), t.length > 0) {
                w.config.maintainColumnRatios = w.config.maintainColumnRatios !== !1;
                var l = 0, s = 0;
                angular.forEach(t, function (e) {
                    var t = i.columns[r[e.index]], n = e.width, o = parseInt(n.slice(0, -1), 10) / 100;
                    l += o, t.visible || (s += o)
                });
                var c = l - s;
                angular.forEach(t, function (e) {
                    var t = i.columns[r[e.index]], n = e.width, a = parseInt(n.slice(0, -1), 10) / 100;
                    a /= s > 0 ? c : l;
                    var g = w.rootDim.outerWidth * l;
                    t.width = Math.floor(g * a), o += t.width
                })
            }
            if (e.length > 0) {
                w.config.maintainColumnRatios = w.config.maintainColumnRatios !== !1;
                var g = w.rootDim.outerWidth - o;
                w.maxCanvasHt > i.viewportDimHeight() && (g -= a.ScrollW);
                var u = Math.floor(g / n);
                angular.forEach(e, function (t, n) {
                    var l = i.columns[r[t.index]];
                    l.width = u * t.width.length, l.visible !== !1 && (o += l.width);
                    var s = n === e.length - 1;
                    if (s && w.rootDim.outerWidth > o) {
                        var c = w.rootDim.outerWidth - o;
                        w.maxCanvasHt > i.viewportDimHeight() && (c -= a.ScrollW), l.width += c
                    }
                })
            }
        }, w.init = function () {
            return w.initTemplates().then(function () {
                i.selectionProvider = new D(w, i, f), i.domAccessProvider = new S(w), w.rowFactory = new R(w, i, a, g, d), w.searchProvider = new $(i, w, c), w.styleProvider = new L(i, w), i.$watch("configGroups", function (e) {
                    var t = [];
                    angular.forEach(e, function (e) {
                        t.push(e.field || e)
                    }), w.config.groups = t, w.rowFactory.filteredRowsChanged(), i.$emit("ngGridEventGroups", e)
                }, !0), i.$watch("columns", function (e) {
                    i.isColumnResizing || a.RebuildGrid(i, w), i.$emit("ngGridEventColumns", e)
                }, !0), i.$watch(function () {
                    return r.i18n
                }, function (e) {
                    d.seti18n(i, e)
                }), w.maxCanvasHt = w.calcMaxCanvasHeight(), w.config.sortInfo.fields && w.config.sortInfo.fields.length > 0 && i.$watch(function () {
                    return w.config.sortInfo
                }, function () {
                    l.isSorting || (w.sortColumnsInit(), i.$emit("ngGridEventSorted", w.config.sortInfo))
                }, !0)
            })
        }, w.resizeOnData = function (e) {
            var n = e.minWidth, o = d.getElementsByClassName("col" + e.index);
            angular.forEach(o, function (e, o) {
                var i;
                if (0 === o) {
                    var r = t(e).find(".ngHeaderText");
                    i = d.visualLength(r) + 10
                } else {
                    var l = t(e).find(".ngCellText");
                    i = d.visualLength(l) + 10
                }
                i > n && (n = i)
            }), e.width = e.longest = Math.min(e.maxWidth, n + 7), a.BuildStyles(i, w, !0)
        }, w.lastSortedColumns = [], w.sortData = function (e, n) {
            if (n && n.shiftKey && w.config.sortInfo) {
                var o = w.config.sortInfo.columns.indexOf(e);
                -1 === o ? (1 === w.config.sortInfo.columns.length && (w.config.sortInfo.columns[0].sortPriority = 1), w.config.sortInfo.columns.push(e), e.sortPriority = w.config.sortInfo.columns.length, w.config.sortInfo.fields.push(e.field), w.config.sortInfo.directions.push(e.sortDirection), w.lastSortedColumns.push(e)) : w.config.sortInfo.directions[o] = e.sortDirection
            } else {
                var r = t.isArray(e);
                w.config.sortInfo.columns.length = 0, w.config.sortInfo.fields.length = 0, w.config.sortInfo.directions.length = 0;
                var l = function (e) {
                    w.config.sortInfo.columns.push(e), w.config.sortInfo.fields.push(e.field), w.config.sortInfo.directions.push(e.sortDirection), w.lastSortedColumns.push(e)
                };
                r ? (w.clearSortingData(), angular.forEach(e, function (e, t) {
                    e.sortPriority = t + 1, l(e)
                })) : (w.clearSortingData(e), e.sortPriority = void 0, l(e))
            }
            w.sortActual(), w.searchProvider.evalFilter(), i.$emit("ngGridEventSorted", w.config.sortInfo)
        }, w.sortColumnsInit = function () {
            w.config.sortInfo.columns ? w.config.sortInfo.columns.length = 0 : w.config.sortInfo.columns = [], angular.forEach(i.columns, function (e) {
                var t = w.config.sortInfo.fields.indexOf(e.field);
                -1 !== t && (e.sortDirection = w.config.sortInfo.directions[t] || "asc", w.config.sortInfo.columns[t] = e)
            }), angular.forEach(w.config.sortInfo.columns, function (e) {
                w.sortData(e)
            })
        }, w.sortActual = function () {
            if (!w.config.useExternalSorting) {
                var e = w.data.slice(0);
                angular.forEach(e, function (e, t) {
                    var n = w.rowMap[t];
                    if (void 0 !== n) {
                        var o = w.rowCache[n];
                        void 0 !== o && (e.preSortSelected = o.selected, e.preSortIndex = t)
                    }
                }), l.Sort(w.config.sortInfo, e), angular.forEach(e, function (e, t) {
                    w.rowCache[t].entity = e, w.rowCache[t].selected = e.preSortSelected, w.rowMap[e.preSortIndex] = t, delete e.preSortSelected, delete e.preSortIndex
                })
            }
        }, w.clearSortingData = function (e) {
            e ? (angular.forEach(w.lastSortedColumns, function (t) {
                e.index !== t.index && (t.sortDirection = "", t.sortPriority = null)
            }), w.lastSortedColumns[0] = e, w.lastSortedColumns.length = 1) : (angular.forEach(w.lastSortedColumns, function (e) {
                e.sortDirection = "", e.sortPriority = null
            }), w.lastSortedColumns = [])
        }, w.fixColumnIndexes = function () {
            for (var e = 0; i.columns.length > e; e++) i.columns[e].index = e
        }, w.fixGroupIndexes = function () {
            angular.forEach(i.configGroups, function (e, t) {
                e.groupIndex = t + 1
            })
        }, i.elementsNeedMeasuring = !0, i.columns = [], i.renderedRows = [], i.renderedColumns = [], i.headerRow = null, i.rowHeight = w.config.rowHeight, i.jqueryUITheme = w.config.jqueryUITheme, i.showSelectionCheckbox = w.config.showSelectionCheckbox, i.enableCellSelection = w.config.enableCellSelection, i.enableCellEditOnFocus = w.config.enableCellEditOnFocus, i.footer = null, i.selectedItems = w.config.selectedItems, i.multiSelect = w.config.multiSelect, i.showFooter = w.config.showFooter, i.footerRowHeight = i.showFooter ? w.config.footerRowHeight : 0, i.showColumnMenu = w.config.showColumnMenu, i.showMenu = !1, i.configGroups = [], i.gridId = w.gridId, i.enablePaging = w.config.enablePaging, i.pagingOptions = w.config.pagingOptions, i.i18n = {}, d.seti18n(i, w.config.i18n), i.adjustScrollLeft = function (e) {
            for (var t = 0, n = 0, o = i.columns.length, r = [], l = !w.config.enableColumnHeavyVirt, s = 0, c = function (e) {
                l ? r.push(e) : i.renderedColumns[s] ? i.renderedColumns[s].setVars(e) : i.renderedColumns[s] = e.copy(), s++
            }, g = 0; o > g; g++) {
                var d = i.columns[g];
                if (d.visible !== !1) {
                    var u = d.width + t;
                    if (d.pinned) {
                        c(d);
                        var f = g > 0 ? e + n : e;
                        a.setColLeft(d, f, w), n += d.width
                    } else u >= e && e + w.rootDim.outerWidth >= t && c(d);
                    t += d.width
                }
            }
            l && (i.renderedColumns = r)
        }, w.prevScrollTop = 0, w.prevScrollIndex = 0, i.adjustScrollTop = function (e, t) {
            if (w.prevScrollTop !== e || t) {
                e > 0 && w.$viewport[0].scrollHeight - e <= w.$viewport.outerHeight() && i.$emit("ngGridEventScroll");
                var r, l = Math.floor(e / w.config.rowHeight);
                if (w.filteredRows.length > w.config.virtualizationThreshold) {
                    if (e > w.prevScrollTop && w.prevScrollIndex + o > l) return;
                    if (w.prevScrollTop > e && l > w.prevScrollIndex - o) return;
                    r = new P(Math.max(0, l - n), l + w.minRowsToRender() + n)
                } else {
                    var a = i.configGroups.length > 0 ? w.rowFactory.parsedData.length : w.data.length;
                    r = new P(0, Math.max(a, w.minRowsToRender() + n))
                }
                w.prevScrollTop = e, w.rowFactory.UpdateViewableRange(r), w.prevScrollIndex = l
            }
        }, i.toggleShowMenu = function () {
            i.showMenu = !i.showMenu
        }, i.toggleSelectAll = function (e, t) {
            i.selectionProvider.toggleSelectAll(e, !1, t)
        }, i.totalFilteredItemsLength = function () {
            return w.filteredRows.length
        }, i.showGroupPanel = function () {
            return w.config.showGroupPanel
        }, i.topPanelHeight = function () {
            return w.config.showGroupPanel === !0 ? w.config.headerRowHeight + 32 : w.config.headerRowHeight
        }, i.viewportDimHeight = function () {
            return Math.max(0, w.rootDim.outerHeight - i.topPanelHeight() - i.footerRowHeight - 2)
        }, i.groupBy = function (e) {
            if (!(1 > w.data.length) && e.groupable && e.field) {
                e.sortDirection || e.sort({shiftKey: i.configGroups.length > 0 ? !0 : !1});
                var t = i.configGroups.indexOf(e);
                -1 === t ? (e.isGroupedBy = !0, i.configGroups.push(e), e.groupIndex = i.configGroups.length) : i.removeGroup(t), w.$viewport.scrollTop(0), a.digest(i)
            }
        }, i.removeGroup = function (e) {
            var t = i.columns.filter(function (t) {
                return t.groupIndex === e + 1
            })[0];
            t.isGroupedBy = !1, t.groupIndex = 0, i.columns[e].isAggCol && (i.columns.splice(e, 1), i.configGroups.splice(e, 1), w.fixGroupIndexes()), 0 === i.configGroups.length && (w.fixColumnIndexes(), a.digest(i)), i.adjustScrollLeft(0)
        }, i.togglePin = function (e) {
            for (var t = e.index, n = 0, o = 0; i.columns.length > o && i.columns[o].pinned; o++) n++;
            e.pinned && (n = Math.max(e.originalIndex, n - 1)), e.pinned = !e.pinned, i.columns.splice(t, 1), i.columns.splice(n, 0, e), w.fixColumnIndexes(), a.BuildStyles(i, w, !0), w.$viewport.scrollLeft(w.$viewport.scrollLeft() - e.width)
        }, i.totalRowWidth = function () {
            for (var e = 0, t = i.columns, n = 0; t.length > n; n++) t[n].visible !== !1 && (e += t[n].width);
            return e
        }, i.headerScrollerDim = function () {
            var e = i.viewportDimHeight(), t = w.maxCanvasHt, n = t > e, o = new b;
            return o.autoFitHeight = !0, o.outerWidth = i.totalRowWidth(), n ? o.outerWidth += w.elementDims.scrollW : w.elementDims.scrollH >= t - e && (o.outerWidth += w.elementDims.scrollW), o
        }
    }, P = function (e, t) {
        this.topRow = e, this.bottomRow = t
    }, I = function (e, t, n, o, i) {
        this.entity = e, this.config = t, this.selectionProvider = n, this.rowIndex = o, this.utils = i, this.selected = n.getSelection(e), this.cursor = this.config.enableRowSelection ? "pointer" : "default", this.beforeSelectionChange = t.beforeSelectionChangeCallback, this.afterSelectionChange = t.afterSelectionChangeCallback, this.offsetTop = this.rowIndex * t.rowHeight, this.rowDisplayIndex = 0
    };
    I.prototype.setSelection = function (e) {
        this.selectionProvider.setSelection(this, e), this.selectionProvider.lastClickedRow = this
    }, I.prototype.continueSelection = function (e) {
        this.selectionProvider.ChangeSelection(this, e)
    }, I.prototype.ensureEntity = function (e) {
        this.entity !== e && (this.entity = e, this.selected = this.selectionProvider.getSelection(this.entity))
    }, I.prototype.toggleSelected = function (e) {
        if (!this.config.enableRowSelection && !this.config.enableCellSelection) return !0;
        var t = e.target || e;
        return "checkbox" === t.type && "ngSelectionCell ng-scope" !== t.parentElement.className ? !0 : this.config.selectWithCheckboxOnly && "checkbox" !== t.type ? (this.selectionProvider.lastClickedRow = this, !0) : (this.beforeSelectionChange(this, e) && this.continueSelection(e), !1)
    }, I.prototype.alternatingRowClass = function () {
        var e = 0 === this.rowIndex % 2, t = {
            ngRow: !0,
            selected: this.selected,
            even: e,
            odd: !e,
            "ui-state-default": this.config.jqueryUITheme && e,
            "ui-state-active": this.config.jqueryUITheme && !e
        };
        return t
    }, I.prototype.getProperty = function (e) {
        return this.utils.evalProperty(this.entity, e)
    }, I.prototype.copy = function () {
        return this.clone = new I(this.entity, this.config, this.selectionProvider, this.rowIndex, this.utils), this.clone.isClone = !0, this.clone.elm = this.elm, this.clone.orig = this, this.clone
    }, I.prototype.setVars = function (e) {
        e.clone = this, this.entity = e.entity, this.selected = e.selected, this.orig = e
    };
    var R = function (e, t, o, i, r) {
        var g = this;
        g.aggCache = {}, g.parentCache = [], g.dataChanged = !0, g.parsedData = [], g.rowConfig = {}, g.selectionProvider = t.selectionProvider, g.rowHeight = 30, g.numberOfAggregates = 0, g.groupedData = void 0, g.rowHeight = e.config.rowHeight, g.rowConfig = {
            enableRowSelection: e.config.enableRowSelection,
            rowClasses: e.config.rowClasses,
            selectedItems: t.selectedItems,
            selectWithCheckboxOnly: e.config.selectWithCheckboxOnly,
            beforeSelectionChangeCallback: e.config.beforeSelectionChange,
            afterSelectionChangeCallback: e.config.afterSelectionChange,
            jqueryUITheme: e.config.jqueryUITheme,
            enableCellSelection: e.config.enableCellSelection,
            rowHeight: e.config.rowHeight
        }, g.renderedRange = new P(0, e.minRowsToRender() + n), g.buildEntityRow = function (e, t) {
            return new I(e, g.rowConfig, g.selectionProvider, t, r)
        }, g.buildAggregateRow = function (t, n) {
            var o = g.aggCache[t.aggIndex];
            return o || (o = new w(t, g, g.rowConfig.rowHeight, e.config.groupsCollapsedByDefault), g.aggCache[t.aggIndex] = o), o.rowIndex = n, o.offsetTop = n * g.rowConfig.rowHeight, o
        }, g.UpdateViewableRange = function (e) {
            g.renderedRange = e, g.renderedChange()
        }, g.filteredRowsChanged = function () {
            e.lateBoundColumns && e.filteredRows.length > 0 && (e.config.columnDefs = void 0, e.buildColumns(), e.lateBoundColumns = !1, t.$evalAsync(function () {
                t.adjustScrollLeft(0)
            })), g.dataChanged = !0, e.config.groups.length > 0 && g.getGrouping(e.config.groups), g.UpdateViewableRange(g.renderedRange)
        }, g.renderedChange = function () {
            if (!g.groupedData || 1 > e.config.groups.length) return g.renderedChangeNoGroups(), e.refreshDomSizes(), void 0;
            g.wasGrouped = !0, g.parentCache = [];
            var t = 0, n = g.parsedData.filter(function (e) {
                return e.isAggRow ? e.parent && e.parent.collapsed ? !1 : !0 : (e[s] || (e.rowIndex = t++), !e[s])
            });
            g.totalRows = n.length;
            for (var o = [], i = g.renderedRange.topRow; g.renderedRange.bottomRow > i; i++) n[i] && (n[i].offsetTop = i * e.config.rowHeight, o.push(n[i]));
            e.setRenderedRows(o)
        }, g.renderedChangeNoGroups = function () {
            for (var t = [], n = g.renderedRange.topRow; g.renderedRange.bottomRow > n; n++) e.filteredRows[n] && (e.filteredRows[n].rowIndex = n, e.filteredRows[n].offsetTop = n * e.config.rowHeight, t.push(e.filteredRows[n]));
            e.setRenderedRows(t)
        }, g.fixRowCache = function () {
            var t = e.data.length, n = t - e.rowCache.length;
            if (0 > n) e.rowCache.length = e.rowMap.length = t; else for (var o = e.rowCache.length; t > o; o++) e.rowCache[o] = e.rowFactory.buildEntityRow(e.data[o], o)
        }, g.parseGroupData = function (e) {
            if (e.values) for (var t = 0; e.values.length > t; t++) g.parentCache[g.parentCache.length - 1].children.push(e.values[t]), g.parsedData.push(e.values[t]); else for (var n in e) if (n !== l && n !== a && n !== c && e.hasOwnProperty(n)) {
                var o = g.buildAggregateRow({
                    gField: e[l],
                    gLabel: n,
                    gDepth: e[a],
                    isAggRow: !0,
                    _ng_hidden_: !1,
                    children: [],
                    aggChildren: [],
                    aggIndex: g.numberOfAggregates,
                    aggLabelFilter: e[c].aggLabelFilter
                }, 0);
                g.numberOfAggregates++, o.parent = g.parentCache[o.depth - 1], o.parent && (o.parent.collapsed = !1, o.parent.aggChildren.push(o)), g.parsedData.push(o), g.parentCache[o.depth] = o, g.parseGroupData(e[n])
            }
        }, g.getGrouping = function (n) {
            function d(e, t) {
                return e.filter(function (e) {
                    return e.field === t
                })
            }

            g.aggCache = [], g.numberOfAggregates = 0, g.groupedData = {};
            for (var u = e.filteredRows, f = n.length, h = t.columns, p = 0; u.length > p; p++) {
                var m = u[p].entity;
                if (!m) return;
                u[p][s] = e.config.groupsCollapsedByDefault;
                for (var v = g.groupedData, w = 0; n.length > w; w++) {
                    var b = n[w], S = d(h, b)[0], x = r.evalProperty(m, b);
                    x = x ? "" + x : "null", v[x] || (v[x] = {}), v[l] || (v[l] = b), v[a] || (v[a] = w), v[c] || (v[c] = S), v = v[x]
                }
                v.values || (v.values = []), v.values.push(u[p])
            }
            if (h.length > 0) for (var y = 0; n.length > y; y++) !h[y].isAggCol && f >= y && h.splice(0, 0, new C({
                colDef: {
                    field: "",
                    width: 25,
                    sortable: !1,
                    resizable: !1,
                    headerCellTemplate: '<div class="ngAggHeader"></div>',
                    pinned: e.config.pinSelectionCheckbox
                }, enablePinning: e.config.enablePinning, isAggCol: !0, headerRowHeight: e.config.headerRowHeight
            }, t, e, o, i, r));
            e.fixColumnIndexes(), t.adjustScrollLeft(0), g.parsedData.length = 0, g.parseGroupData(g.groupedData), g.fixRowCache()
        }, e.config.groups.length > 0 && e.filteredRows.length > 0 && g.getGrouping(e.config.groups)
    }, $ = function (e, n, o) {
        var i = this, r = [];
        i.extFilter = n.config.filterOptions.useExternalFilter, e.showFilter = n.config.showFilter, e.filterText = "", i.fieldMap = {};
        var l = function (e, t, n) {
            var i;
            for (var r in t) if (t.hasOwnProperty(r)) {
                var a = n[r.toLowerCase()];
                if (!a) continue;
                var s = t[r];
                if ("object" == typeof s) return l(e, s, a);
                var c = null, g = null;
                if (a && a.cellFilter && (g = a.cellFilter.split(":"), c = o(g[0])), null !== s && void 0 !== s) {
                    if ("function" == typeof c) {
                        var d = "" + c(s, g[1]);
                        i = e.regex.test(d)
                    } else i = e.regex.test("" + s);
                    if (i) return !0
                }
            }
            return !1
        }, a = function (e, t) {
            var n, r = i.fieldMap[e.columnDisplay];
            if (!r) return !1;
            var l = r.cellFilter.split(":"), a = r.cellFilter ? o(l[0]) : null,
                s = t[e.column] || t[r.field.split(".")[0]];
            if (null === s || void 0 === s) return !1;
            if ("function" == typeof a) {
                var g = "" + a("object" == typeof s ? c(s, r.field) : s, l[1]);
                n = e.regex.test(g)
            } else n = e.regex.test("object" == typeof s ? "" + c(s, r.field) : "" + s);
            return n ? !0 : !1
        }, s = function (e) {
            for (var t = 0, n = r.length; n > t; t++) {
                var o, s = r[t];
                if (o = s.column ? a(s, e) : l(s, e, i.fieldMap), !o) return !1
            }
            return !0
        };
        i.evalFilter = function () {
            n.filteredRows = 0 === r.length ? n.rowCache : n.rowCache.filter(function (e) {
                return s(e.entity)
            });
            for (var e = 0; n.filteredRows.length > e; e++) n.filteredRows[e].rowIndex = e;
            n.rowFactory.filteredRowsChanged()
        };
        var c = function (e, t) {
            if ("object" != typeof e || "string" != typeof t) return e;
            var n = t.split("."), o = e;
            if (n.length > 1) {
                for (var i = 1, r = n.length; r > i; i++) if (o = o[n[i]], !o) return e;
                return o
            }
            return e
        }, g = function (e, t) {
            try {
                return RegExp(e, t)
            } catch (n) {
                return RegExp(e.replace(/(\^|\$|\(|\)|<|>|\[|\]|\{|\}|\\|\||\.|\*|\+|\?)/g, "\\$1"))
            }
        }, d = function (e) {
            r = [];
            var n;
            if (n = t.trim(e)) for (var o = n.split(";"), i = 0; o.length > i; i++) {
                var l = o[i].split(":");
                if (l.length > 1) {
                    var a = t.trim(l[0]), s = t.trim(l[1]);
                    a && s && r.push({column: a, columnDisplay: a.replace(/\s+/g, "").toLowerCase(), regex: g(s, "i")})
                } else {
                    var c = t.trim(l[0]);
                    c && r.push({column: "", regex: g(c, "i")})
                }
            }
        };
        i.extFilter || e.$watch("columns", function (e) {
            for (var t = 0; e.length > t; t++) {
                var n = e[t];
                if (n.field) if (n.field.match(/\./g)) {
                    for (var o = n.field.split("."), r = i.fieldMap, l = 0; o.length - 1 > l; l++) r[o[l]] = r[o[l]] || {}, r = r[o[l]];
                    r[o[o.length - 1]] = n
                } else i.fieldMap[n.field.toLowerCase()] = n;
                n.displayName && (i.fieldMap[n.displayName.toLowerCase().replace(/\s+/g, "")] = n)
            }
        }), e.$watch(function () {
            return n.config.filterOptions.filterText
        }, function (t) {
            e.filterText = t
        }), e.$watch("filterText", function (t) {
            i.extFilter || (e.$emit("ngGridEventFilter", t), d(t), i.evalFilter())
        })
    }, D = function (e, t, n) {
        var o = this;
        o.multi = e.config.multiSelect, o.selectedItems = e.config.selectedItems, o.selectedIndex = e.config.selectedIndex, o.lastClickedRow = void 0, o.ignoreSelectedItemChanges = !1, o.pKeyParser = n(e.config.primaryKey), o.ChangeSelection = function (n, i) {
            var r = i.which || i.keyCode, l = 40 === r || 38 === r;
            if (i && i.shiftKey && !i.keyCode && o.multi && e.config.enableRowSelection) {
                if (o.lastClickedRow) {
                    var a;
                    a = t.configGroups.length > 0 ? e.rowFactory.parsedData.filter(function (e) {
                        return !e.isAggRow
                    }) : e.filteredRows;
                    var s = n.rowIndex, c = o.lastClickedRowIndex;
                    if (s === c) return !1;
                    c > s ? (s ^= c, c = s ^ c, s ^= c, s--) : c++;
                    for (var g = []; s >= c; c++) g.push(a[c]);
                    if (g[g.length - 1].beforeSelectionChange(g, i)) {
                        for (var d = 0; g.length > d; d++) {
                            var u = g[d], f = u.selected;
                            u.selected = !f, u.clone && (u.clone.selected = u.selected);
                            var h = o.selectedItems.indexOf(u.entity);
                            -1 === h ? o.selectedItems.push(u.entity) : o.selectedItems.splice(h, 1)
                        }
                        g[g.length - 1].afterSelectionChange(g, i)
                    }
                    return o.lastClickedRow = n, o.lastClickedRowIndex = n.rowIndex, !0
                }
            } else o.multi ? (!i.keyCode || l && !e.config.selectWithCheckboxOnly) && o.setSelection(n, !n.selected) : o.lastClickedRow === n ? o.setSelection(o.lastClickedRow, e.config.keepLastSelected ? !0 : !n.selected) : (o.lastClickedRow && o.setSelection(o.lastClickedRow, !1), o.setSelection(n, !n.selected));
            return o.lastClickedRow = n, o.lastClickedRowIndex = n.rowIndex, !0
        }, o.getSelection = function (t) {
            var n = !1;
            if (e.config.primaryKey) {
                var i = o.pKeyParser(t);
                angular.forEach(o.selectedItems, function (e) {
                    i === o.pKeyParser(e) && (n = !0)
                })
            } else n = -1 !== o.selectedItems.indexOf(t);
            return n
        }, o.setSelection = function (t, n) {
            if (e.config.enableRowSelection) {
                if (n) -1 === o.selectedItems.indexOf(t.entity) && (!o.multi && o.selectedItems.length > 0 && o.toggleSelectAll(!1, !0), o.selectedItems.push(t.entity)); else {
                    var i = o.selectedItems.indexOf(t.entity);
                    -1 !== i && o.selectedItems.splice(i, 1)
                }
                t.selected = n, t.orig && (t.orig.selected = n), t.clone && (t.clone.selected = n), t.afterSelectionChange(t)
            }
        }, o.toggleSelectAll = function (t, n, i) {
            var r = i ? e.filteredRows : e.rowCache;
            if (n || e.config.beforeSelectionChange(r, t)) {
                var l = o.selectedItems.length;
                l > 0 && (o.selectedItems.length = 0);
                for (var a = 0; r.length > a; a++) r[a].selected = t, r[a].clone && (r[a].clone.selected = t), t && o.selectedItems.push(r[a].entity);
                n || e.config.afterSelectionChange(r, t)
            }
        }
    }, L = function (e, t) {
        e.headerCellStyle = function (e) {
            return {height: e.headerRowHeight + "px"}
        }, e.rowStyle = function (t) {
            var n = {top: t.offsetTop + "px", height: e.rowHeight + "px"};
            return t.isAggRow && (n.left = t.offsetLeft), n
        }, e.canvasStyle = function () {
            return {height: t.maxCanvasHt + "px"}
        }, e.headerScrollerStyle = function () {
            return {height: t.config.headerRowHeight + "px"}
        }, e.topPanelStyle = function () {
            return {width: t.rootDim.outerWidth + "px", height: e.topPanelHeight() + "px"}
        }, e.headerStyle = function () {
            return {width: t.rootDim.outerWidth + "px", height: t.config.headerRowHeight + "px"}
        }, e.groupPanelStyle = function () {
            return {width: t.rootDim.outerWidth + "px", height: "32px"}
        }, e.viewportStyle = function () {
            return {width: t.rootDim.outerWidth + "px", height: e.viewportDimHeight() + "px"}
        }, e.footerStyle = function () {
            return {width: t.rootDim.outerWidth + "px", height: e.footerRowHeight + "px"}
        }
    };
    p.directive("ngCellHasFocus", ["$domUtilityService", function (e) {
        var t = function (t) {
            t.isFocused = !0, e.digest(t), t.$broadcast("ngGridEventStartCellEdit"), t.$on("ngGridEventEndCellEdit", function () {
                t.isFocused = !1, e.digest(t)
            })
        };
        return function (e, n) {
            var o = !1, i = !1;
            e.editCell = function () {
                e.enableCellEditOnFocus || setTimeout(function () {
                    t(e, n)
                }, 0)
            }, n.bind("mousedown", function () {
                return e.enableCellEditOnFocus ? i = !0 : n.focus(), !0
            }), n.bind("click", function (o) {
                e.enableCellEditOnFocus && (o.preventDefault(), i = !1, t(e, n))
            }), n.bind("focus", function () {
                return o = !0, e.enableCellEditOnFocus && !i && t(e, n), !0
            }), n.bind("blur", function () {
                return o = !1, !0
            }), n.bind("keydown", function (i) {
                return e.enableCellEditOnFocus || (o && 37 !== i.keyCode && 38 !== i.keyCode && 39 !== i.keyCode && 40 !== i.keyCode && 9 !== i.keyCode && !i.shiftKey && 13 !== i.keyCode && t(e, n), o && i.shiftKey && i.keyCode >= 65 && 90 >= i.keyCode && t(e, n), 27 === i.keyCode && n.focus()), !0
            })
        }
    }]), p.directive("ngCellText", function () {
        return function (e, t) {
            t.bind("mouseover", function (e) {
                e.preventDefault(), t.css({cursor: "text"})
            }), t.bind("mouseleave", function (e) {
                e.preventDefault(), t.css({cursor: "default"})
            })
        }
    }), p.directive("ngCell", ["$compile", "$domUtilityService", function (e, t) {
        var n = {
            scope: !1, compile: function () {
                return {
                    pre: function (t, n) {
                        var o, i = t.col.cellTemplate.replace(d, "row.entity." + t.col.field);
                        t.col.enableCellEdit ? (o = t.col.cellEditTemplate, o = o.replace(u, i), o = o.replace(f, t.col.editableCellTemplate.replace(d, "row.entity." + t.col.field))) : o = i;
                        var r = e(o)(t);
                        t.enableCellSelection && -1 === r[0].className.indexOf("ngSelectionCell") && (r[0].setAttribute("tabindex", 0), r.addClass("ngCellElement")), n.append(r)
                    }, post: function (e, n) {
                        e.enableCellSelection && e.domAccessProvider.selectionHandlers(e, n), e.$on("ngGridEventDigestCell", function () {
                            t.digest(e)
                        })
                    }
                }
            }
        };
        return n
    }]), p.directive("ngEditCellIf", [function () {
        return {
            transclude: "element", priority: 1e3, terminal: !0, restrict: "A", compile: function (e, t, n) {
                return function (e, t, o) {
                    var i, r;
                    e.$watch(o.ngEditCellIf, function (o) {
                        i && (i.remove(), i = void 0), r && (r.$destroy(), r = void 0), o && (r = e.$new(), n(r, function (e) {
                            i = e, t.after(e)
                        }))
                    })
                }
            }
        }
    }]), p.directive("ngGridFooter", ["$compile", "$templateCache", function (e, t) {
        var n = {
            scope: !1, compile: function () {
                return {
                    pre: function (n, o) {
                        0 === o.children().length && o.append(e(t.get(n.gridId + "footerTemplate.html"))(n))
                    }
                }
            }
        };
        return n
    }]), p.directive("ngGridMenu", ["$compile", "$templateCache", function (e, t) {
        var n = {
            scope: !1, compile: function () {
                return {
                    pre: function (n, o) {
                        0 === o.children().length && o.append(e(t.get(n.gridId + "menuTemplate.html"))(n))
                    }
                }
            }
        };
        return n
    }]), p.directive("ngGrid", ["$compile", "$filter", "$templateCache", "$sortService", "$domUtilityService", "$utilityService", "$timeout", "$parse", "$http", "$q", function (e, n, o, i, r, l, a, s, c, g) {
        var d = {
            scope: !0, compile: function () {
                return {
                    pre: function (d, u, f) {
                        var h = t(u), p = d.$eval(f.ngGrid);
                        p.gridDim = new b({outerHeight: t(h).height(), outerWidth: t(h).width()});
                        var m = new T(d, p, i, r, n, o, l, a, s, c, g);
                        return m.init().then(function () {
                            if ("string" == typeof p.columnDefs ? d.$parent.$watch(p.columnDefs, function (e) {
                                return e ? (m.lateBoundColumns = !1, d.columns = [], m.config.columnDefs = e, m.buildColumns(), m.eventProvider.assignEvents(), r.RebuildGrid(d, m), void 0) : (m.refreshDomSizes(), m.buildColumns(), void 0)
                            }, !0) : m.buildColumns(), "string" == typeof p.totalServerItems ? d.$parent.$watch(p.totalServerItems, function (e) {
                                d.totalServerItems = angular.isDefined(e) ? e : 0
                            }) : d.totalServerItems = 0, "string" == typeof p.data) {
                                var n = function (e) {
                                    m.data = t.extend([], e), m.rowFactory.fixRowCache(), angular.forEach(m.data, function (e, t) {
                                        var n = m.rowMap[t] || t;
                                        m.rowCache[n] && m.rowCache[n].ensureEntity(e), m.rowMap[n] = t
                                    }), m.searchProvider.evalFilter(), m.configureColumnWidths(), m.refreshDomSizes(), m.config.sortInfo.fields.length > 0 && (m.sortColumnsInit(), d.$emit("ngGridEventSorted", m.config.sortInfo)), d.$emit("ngGridEventData", m.gridId)
                                };
                                d.$parent.$watch(p.data, n), d.$parent.$watch(p.data + ".length", function () {
                                    n(d.$eval(p.data))
                                })
                            }
                            return m.footerController = new y(d, m), u.addClass("ngGrid").addClass("" + m.gridId), p.enableHighlighting || u.addClass("unselectable"), p.jqueryUITheme && u.addClass("ui-widget"), u.append(e(o.get("gridTemplate.html"))(d)), r.AssignGridContainers(d, u, m), m.eventProvider = new x(m, d, r, a), p.selectRow = function (e, t) {
                                m.rowCache[e] && (m.rowCache[e].clone && m.rowCache[e].clone.setSelection(t ? !0 : !1), m.rowCache[e].setSelection(t ? !0 : !1))
                            }, p.selectItem = function (e, t) {
                                p.selectRow(m.rowMap[e], t)
                            }, p.selectAll = function (e) {
                                d.toggleSelectAll(e)
                            }, p.selectVisible = function (e) {
                                d.toggleSelectAll(e, !0)
                            }, p.groupBy = function (e) {
                                if (e) d.groupBy(d.columns.filter(function (t) {
                                    return t.field === e
                                })[0]); else {
                                    var n = t.extend(!0, [], d.configGroups);
                                    angular.forEach(n, d.groupBy)
                                }
                            }, p.sortBy = function (e) {
                                var t = d.columns.filter(function (t) {
                                    return t.field === e
                                })[0];
                                t && t.sort()
                            }, p.gridId = m.gridId, p.ngGrid = m, p.$gridScope = d, p.$gridServices = {
                                SortService: i,
                                DomUtilityService: r,
                                UtilityService: l
                            }, d.$on("ngGridEventDigestGrid", function () {
                                r.digest(d.$parent)
                            }), d.$on("ngGridEventDigestGridParent", function () {
                                r.digest(d.$parent)
                            }), d.$evalAsync(function () {
                                d.adjustScrollLeft(0)
                            }), angular.forEach(p.plugins, function (e) {
                                "function" == typeof e && (e = new e), e.init(d.$new(), m, p.$gridServices), p.plugins[l.getInstanceType(e)] = e
                            }), "function" == typeof p.init && p.init(m, d), null
                        })
                    }
                }
            }
        };
        return d
    }]), p.directive("ngHeaderCell", ["$compile", function (e) {
        var t = {
            scope: !1, compile: function () {
                return {
                    pre: function (t, n) {
                        n.append(e(t.col.headerCellTemplate)(t))
                    }
                }
            }
        };
        return t
    }]), p.directive("ngInput", [function () {
        return {
            require: "ngModel", link: function (e, t, n, o) {
                var i, r = e.$watch("ngModel", function () {
                    i = o.$modelValue, r()
                });
                t.bind("keydown", function (n) {
                    switch (n.keyCode) {
                        case 37:
                        case 38:
                        case 39:
                        case 40:
                            n.stopPropagation();
                            break;
                        case 27:
                            e.$$phase || e.$apply(function () {
                                o.$setViewValue(i), t.blur()
                            });
                            break;
                        case 13:
                            (e.enableCellEditOnFocus && e.totalFilteredItemsLength() - 1 > e.row.rowIndex && e.row.rowIndex > 0 || e.enableCellEdit) && t.blur()
                    }
                    return !0
                }), t.bind("click", function (e) {
                    e.stopPropagation()
                }), t.bind("mousedown", function (e) {
                    e.stopPropagation()
                }), e.$on("ngGridEventStartCellEdit", function () {
                    t.focus(), t.select()
                }), angular.element(t).bind("blur", function () {
                    e.$emit("ngGridEventEndCellEdit")
                })
            }
        }
    }]), p.directive("ngRow", ["$compile", "$domUtilityService", "$templateCache", function (e, t, n) {
        var o = {
            scope: !1, compile: function () {
                return {
                    pre: function (o, i) {
                        if (o.row.elm = i, o.row.clone && (o.row.clone.elm = i), o.row.isAggRow) {
                            var r = n.get(o.gridId + "aggregateTemplate.html");
                            r = o.row.aggLabelFilter ? r.replace(g, "| " + o.row.aggLabelFilter) : r.replace(g, ""), i.append(e(r)(o))
                        } else i.append(e(n.get(o.gridId + "rowTemplate.html"))(o));
                        o.$on("ngGridEventDigestRow", function () {
                            t.digest(o)
                        })
                    }
                }
            }
        };
        return o
    }]), p.directive("ngViewport", [function () {
        return function (e, t) {
            var n, o, i = 0;
            t.bind("scroll", function (t) {
                var r = t.target.scrollLeft, l = t.target.scrollTop;
                return e.$headerContainer && e.$headerContainer.scrollLeft(r), e.adjustScrollLeft(r), e.adjustScrollTop(l), e.$root.$$phase || e.$digest(), o = r, i = l, n = !1, !0
            }), t.bind("mousewheel DOMMouseScroll", function () {
                return n = !0, t.focus && t.focus(), !0
            }), e.enableCellSelection || e.domAccessProvider.selectionHandlers(e, t)
        }
    }]), e.ngGrid.i18n.da = {
        ngAggregateLabel: "artikler",
        ngGroupPanelDescription: "Grupér rækker udfra en kolonne ved at trække dens overskift hertil.",
        ngSearchPlaceHolder: "Søg...",
        ngMenuText: "Vælg kolonner:",
        ngShowingItemsLabel: "Viste rækker:",
        ngTotalItemsLabel: "Rækker totalt:",
        ngSelectedItemsLabel: "Valgte rækker:",
        ngPageSizeLabel: "Side størrelse:",
        ngPagerFirstTitle: "Første side",
        ngPagerNextTitle: "Næste side",
        ngPagerPrevTitle: "Forrige side",
        ngPagerLastTitle: "Sidste side"
    }, e.ngGrid.i18n.de = {
        ngAggregateLabel: "artikel",
        ngGroupPanelDescription: "Ziehen Sie eine Spaltenüberschrift hier und legen Sie es der Gruppe nach dieser Spalte.",
        ngSearchPlaceHolder: "Suche...",
        ngMenuText: "Spalten auswählen:",
        ngShowingItemsLabel: "Zeige Artikel:",
        ngTotalItemsLabel: "Meiste Artikel:",
        ngSelectedItemsLabel: "Ausgewählte Artikel:",
        ngPageSizeLabel: "Größe Seite:",
        ngPagerFirstTitle: "Erste Page",
        ngPagerNextTitle: "Nächste Page",
        ngPagerPrevTitle: "Vorherige Page",
        ngPagerLastTitle: "Letzte Page"
    }, e.ngGrid.i18n.en = {
        ngAggregateLabel: "items",
        ngGroupPanelDescription: "Drag a column header here and drop it to group by that column.",
        ngSearchPlaceHolder: "Search...",
        ngMenuText: "Choose Columns:",
        ngShowingItemsLabel: "Showing Items:",
        ngTotalItemsLabel: "Total Items:",
        ngSelectedItemsLabel: "Selected Items:",
        ngPageSizeLabel: "Page Size:",
        ngPagerFirstTitle: "First Page",
        ngPagerNextTitle: "Next Page",
        ngPagerPrevTitle: "Previous Page",
        ngPagerLastTitle: "Last Page"
    }, e.ngGrid.i18n.es = {
        ngAggregateLabel: "Artículos",
        ngGroupPanelDescription: "Arrastre un encabezado de columna aquí y soltarlo para agrupar por esa columna.",
        ngSearchPlaceHolder: "Buscar...",
        ngMenuText: "Elegir columnas:",
        ngShowingItemsLabel: "Artículos Mostrando:",
        ngTotalItemsLabel: "Artículos Totales:",
        ngSelectedItemsLabel: "Artículos Seleccionados:",
        ngPageSizeLabel: "Tamaño de Página:",
        ngPagerFirstTitle: "Primera Página",
        ngPagerNextTitle: "Página Siguiente",
        ngPagerPrevTitle: "Página Anterior",
        ngPagerLastTitle: "Última Página"
    }, e.ngGrid.i18n.fr = {
        ngAggregateLabel: "articles",
        ngGroupPanelDescription: "Faites glisser un en-tête de colonne ici et déposez-le vers un groupe par cette colonne.",
        ngSearchPlaceHolder: "Recherche...",
        ngMenuText: "Choisir des colonnes:",
        ngShowingItemsLabel: "Articles Affichage des:",
        ngTotalItemsLabel: "Nombre total d'articles:",
        ngSelectedItemsLabel: "Éléments Articles:",
        ngPageSizeLabel: "Taille de page:",
        ngPagerFirstTitle: "Première page",
        ngPagerNextTitle: "Page Suivante",
        ngPagerPrevTitle: "Page précédente",
        ngPagerLastTitle: "Dernière page"
    }, e.ngGrid.i18n["pt-br"] = {
        ngAggregateLabel: "items",
        ngGroupPanelDescription: "Arraste e solte uma coluna aqui para agrupar por essa coluna",
        ngSearchPlaceHolder: "Procurar...",
        ngMenuText: "Selecione as colunas:",
        ngShowingItemsLabel: "Mostrando os Items:",
        ngTotalItemsLabel: "Total de Items:",
        ngSelectedItemsLabel: "Items Selecionados:",
        ngPageSizeLabel: "Tamanho da Página:",
        ngPagerFirstTitle: "Primeira Página",
        ngPagerNextTitle: "Próxima Página",
        ngPagerPrevTitle: "Página Anterior",
        ngPagerLastTitle: "Última Página"
    }, e.ngGrid.i18n["zh-cn"] = {
        ngAggregateLabel: "条目",
        ngGroupPanelDescription: "拖曳表头到此处以进行分组",
        ngSearchPlaceHolder: "搜索...",
        ngMenuText: "数据分组与选择列：",
        ngShowingItemsLabel: "当前显示条目：",
        ngTotalItemsLabel: "条目总数：",
        ngSelectedItemsLabel: "选中条目：",
        ngPageSizeLabel: "每页显示数：",
        ngPagerFirstTitle: "回到首页",
        ngPagerNextTitle: "下一页",
        ngPagerPrevTitle: "上一页",
        ngPagerLastTitle: "前往尾页"
    }, e.ngGrid.i18n["zh-tw"] = {
        ngAggregateLabel: "筆",
        ngGroupPanelDescription: "拖拉表頭到此處以進行分組",
        ngSearchPlaceHolder: "搜尋...",
        ngMenuText: "選擇欄位：",
        ngShowingItemsLabel: "目前顯示筆數：",
        ngTotalItemsLabel: "總筆數：",
        ngSelectedItemsLabel: "選取筆數：",
        ngPageSizeLabel: "每頁顯示：",
        ngPagerFirstTitle: "第一頁",
        ngPagerNextTitle: "下一頁",
        ngPagerPrevTitle: "上一頁",
        ngPagerLastTitle: "最後頁"
    }, angular.module("ngGrid").run(["$templateCache", function (e) {
        e.put("aggregateTemplate.html", '<div ng-click="row.toggleExpand()" ng-style="rowStyle(row)" class="ngAggregate">    <span class="ngAggregateText">{{row.label CUSTOM_FILTERS}} ({{row.totalChildren()}} {{AggItemsLabel}})</span>    <div class="{{row.aggClass()}}"></div></div>'), e.put("cellEditTemplate.html", '<div ng-cell-has-focus ng-dblclick="editCell()">	<div ng-edit-cell-if="!isFocused">			DISPLAY_CELL_TEMPLATE	</div>	<div ng-edit-cell-if="isFocused">		EDITABLE_CELL_TEMPLATE	</div></div>'), e.put("cellTemplate.html", '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{COL_FIELD CUSTOM_FILTERS}}</span></div>'), e.put("checkboxCellTemplate.html", '<div class="ngSelectionCell"><input tabindex="-1" class="ngSelectionCheckbox" type="checkbox" ng-checked="row.selected" /></div>'), e.put("checkboxHeaderTemplate.html", '<input class="ngSelectionHeader" type="checkbox" ng-show="multiSelect" ng-model="allSelected" ng-change="toggleSelectAll(allSelected, true)"/>'), e.put("editableCellTemplate.html", '<input ng-class="\'colt\' + col.index" ng-input="COL_FIELD" ng-model="COL_FIELD" />'), e.put("footerTemplate.html", '<div ng-show="showFooter" class="ngFooterPanel" ng-class="{\'ui-widget-content\': jqueryUITheme, \'ui-corner-bottom\': jqueryUITheme}" ng-style="footerStyle()">    <div class="ngTotalSelectContainer" >        <div class="ngFooterTotalItems" ng-class="{\'ngNoMultiSelect\': !multiSelect}" >            <span class="ngLabel">{{i18n.ngTotalItemsLabel}} {{maxRows()}}</span><span ng-show="filterText.length > 0" class="ngLabel">({{i18n.ngShowingItemsLabel}} {{totalFilteredItemsLength()}})</span>        </div>        <div class="ngFooterSelectedItems" ng-show="multiSelect">            <span class="ngLabel">{{i18n.ngSelectedItemsLabel}} {{selectedItems.length}}</span>        </div>    </div>    <div class="ngPagerContainer" style="float: right; margin-top: 10px;" ng-show="enablePaging" ng-class="{\'ngNoMultiSelect\': !multiSelect}">        <div style="float:left; margin-right: 10px;" class="ngRowCountPicker">            <span style="float: left; margin-top: 3px;" class="ngLabel">{{i18n.ngPageSizeLabel}}</span>            <select style="float: left;height: 27px; width: 100px" ng-model="pagingOptions.pageSize" >                <option ng-repeat="size in pagingOptions.pageSizes">{{size}}</option>            </select>        </div>        <div style="float:left; margin-right: 10px; line-height:25px;" class="ngPagerControl" style="float: left; min-width: 135px;">            <button class="ngPagerButton" ng-click="pageToFirst()" ng-disabled="cantPageBackward()" title="{{i18n.ngPagerFirstTitle}}"><div class="ngPagerFirstTriangle"><div class="ngPagerFirstBar"></div></div></button>            <button class="ngPagerButton" ng-click="pageBackward()" ng-disabled="cantPageBackward()" title="{{i18n.ngPagerPrevTitle}}"><div class="ngPagerFirstTriangle ngPagerPrevTriangle"></div></button>            <input class="ngPagerCurrent" min="1" max="{{maxPages()}}" type="number" style="width:50px; height: 24px; margin-top: 1px; padding: 0 4px;" ng-model="pagingOptions.currentPage"/>            <button class="ngPagerButton" ng-click="pageForward()" ng-disabled="cantPageForward()" title="{{i18n.ngPagerNextTitle}}"><div class="ngPagerLastTriangle ngPagerNextTriangle"></div></button>            <button class="ngPagerButton" ng-click="pageToLast()" ng-disabled="cantPageToLast()" title="{{i18n.ngPagerLastTitle}}"><div class="ngPagerLastTriangle"><div class="ngPagerLastBar"></div></div></button>        </div>    </div></div>'), e.put("gridTemplate.html", '<div class="ngTopPanel" ng-class="{\'ui-widget-header\':jqueryUITheme, \'ui-corner-top\': jqueryUITheme}" ng-style="topPanelStyle()">    <div class="ngGroupPanel" ng-show="showGroupPanel()" ng-style="groupPanelStyle()">        <div class="ngGroupPanelDescription" ng-show="configGroups.length == 0">{{i18n.ngGroupPanelDescription}}</div>        <ul ng-show="configGroups.length > 0" class="ngGroupList">            <li class="ngGroupItem" ng-repeat="group in configGroups">                <span class="ngGroupElement">                    <span class="ngGroupName">{{group.displayName}}                        <span ng-click="removeGroup($index)" class="ngRemoveGroup">x</span>                    </span>                    <span ng-hide="$last" class="ngGroupArrow"></span>                </span>            </li>        </ul>    </div>    <div class="ngHeaderContainer" ng-style="headerStyle()">        <div class="ngHeaderScroller" ng-style="headerScrollerStyle()" ng-include="gridId + \'headerRowTemplate.html\'"></div>    </div>    <div ng-grid-menu></div></div><div class="ngViewport" unselectable="on" ng-viewport ng-class="{\'ui-widget-content\': jqueryUITheme}" ng-style="viewportStyle()">    <div class="ngCanvas" ng-style="canvasStyle()">        <div ng-style="rowStyle(row)" ng-repeat="row in renderedRows" ng-click="row.toggleSelected($event)" ng-class="row.alternatingRowClass()" ng-row></div>    </div></div><div ng-grid-footer></div>'), e.put("headerCellTemplate.html", '<div class="ngHeaderSortColumn {{col.headerClass}}" ng-style="{\'cursor\': col.cursor}" ng-class="{ \'ngSorted\': !noSortVisible }">    <div ng-click="col.sort($event)" ng-class="\'colt\' + col.index" class="ngHeaderText">{{col.displayName}}</div>    <div class="ngSortButtonDown" ng-show="col.showSortButtonDown()"></div>    <div class="ngSortButtonUp" ng-show="col.showSortButtonUp()"></div>    <div class="ngSortPriority">{{col.sortPriority}}</div>    <div ng-class="{ ngPinnedIcon: col.pinned, ngUnPinnedIcon: !col.pinned }" ng-click="togglePin(col)" ng-show="col.pinnable"></div></div><div ng-show="col.resizable" class="ngHeaderGrip" ng-click="col.gripClick($event)" ng-mousedown="col.gripOnMouseDown($event)"></div>'), e.put("headerRowTemplate.html", '<div ng-style="{ height: col.headerRowHeight }" ng-repeat="col in renderedColumns" ng-class="col.colIndex()" class="ngHeaderCell">	<div class="ngVerticalBar" ng-style="{height: col.headerRowHeight}" ng-class="{ ngVerticalBarVisible: !$last }">&nbsp;</div>	<div ng-header-cell></div></div>'), e.put("menuTemplate.html", '<div ng-show="showColumnMenu || showFilter"  class="ngHeaderButton" ng-click="toggleShowMenu()">    <div class="ngHeaderButtonArrow"></div></div><div ng-show="showMenu" class="ngColMenu">    <div ng-show="showFilter">        <input placeholder="{{i18n.ngSearchPlaceHolder}}" type="text" ng-model="filterText"/>    </div>    <div ng-show="showColumnMenu">        <span class="ngMenuText">{{i18n.ngMenuText}}</span>        <ul class="ngColList">            <li class="ngColListItem" ng-repeat="col in columns | ngColumns">                <label><input ng-disabled="col.pinned" type="checkbox" class="ngColListCheckbox" ng-model="col.visible"/>{{col.displayName}}</label>				<a title="Group By" ng-class="col.groupedByClass()" ng-show="col.groupable && col.visible" ng-click="groupBy(col)"></a>				<span class="ngGroupingNumber" ng-show="col.groupIndex > 0">{{col.groupIndex}}</span>                      </li>        </ul>    </div></div>'), e.put("rowTemplate.html", '<div ng-style="{ \'cursor\': row.cursor }" ng-repeat="col in renderedColumns" ng-class="col.colIndex()" class="ngCell {{col.cellClass}}">	<div class="ngVerticalBar" ng-style="{height: rowHeight}" ng-class="{ ngVerticalBarVisible: !$last }">&nbsp;</div>	<div ng-cell></div></div>')
    }])
})(window, jQuery);