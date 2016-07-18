package main;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class CopySheets
{

	public XSSFWorkbook mergeExcelFiles(XSSFWorkbook book, ArrayList<FileInputStream> inList) throws IOException
	{

		for (FileInputStream fin : inList)
		{
			XSSFWorkbook b = new XSSFWorkbook(fin);
			for (int i = 0; i < b.getNumberOfSheets(); i++)
			{
				// not entering sheet name, because of duplicated names
				String sheetName = b.getSheetAt(i).getSheetName();
				
				XSSFSheet sheet = book.getSheet(sheetName);
				if(sheet == null){
					copySheets(book.createSheet(sheetName), b.getSheetAt(i));
				}
				else{
					book.removeSheetAt(book.getSheetIndex(sheetName));
					copySheets(book.createSheet(sheetName), b.getSheetAt(i));
				}
				
				
			}
		}
		return book;
	}

	/**
	 * @param newSheet
	 *            the sheet to create from the copy.
	 * @param sheet
	 *            the sheet to copy.
	 */
	public static void copySheets(XSSFSheet newSheet, XSSFSheet sheet)
	{
		copySheets(newSheet, sheet, true);
	}

	/**
	 * @param newSheet
	 *            the sheet to create from the copy.
	 * @param sheet
	 *            the sheet to copy.
	 * @param copyStyle
	 *            true copy the style.
	 */
	public static void copySheets(XSSFSheet newSheet, XSSFSheet sheet, boolean copyStyle)
	{
		int maxColumnNum = 0;
		Map<Integer, XSSFCellStyle> styleMap = (copyStyle) ? new HashMap<Integer, XSSFCellStyle>() : null;
		for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++)
		{
			XSSFRow srcRow = sheet.getRow(i);
			XSSFRow destRow = newSheet.createRow(i);
			if (srcRow != null)
			{
				copyRow(sheet, newSheet, srcRow, destRow, styleMap);
				if (srcRow.getLastCellNum() > maxColumnNum)
				{
					maxColumnNum = srcRow.getLastCellNum();
				}
			}
		}
		for (int i = 0; i <= maxColumnNum; i++)
		{
			newSheet.setColumnWidth(i, sheet.getColumnWidth(i));
		}
	}

	/**
	 * @param srcSheet
	 *            the sheet to copy.
	 * @param destSheet
	 *            the sheet to create.
	 * @param srcRow
	 *            the row to copy.
	 * @param destRow
	 *            the row to create.
	 * @param styleMap
	 *            -
	 */
	public static void copyRow(XSSFSheet srcSheet, XSSFSheet destSheet, XSSFRow srcRow, XSSFRow destRow,
			Map<Integer, XSSFCellStyle> styleMap)
	{
		// manage a list of merged zone in order to not insert two times a
		// merged zone
		Set<CellRangeAddressWrapper> mergedRegions = new TreeSet<CellRangeAddressWrapper>();
		destRow.setHeight(srcRow.getHeight());
		// reckoning delta rows
		int deltaRows = destRow.getRowNum() - srcRow.getRowNum();
		// pour chaque row
		for (int j = srcRow.getFirstCellNum(); j <= srcRow.getLastCellNum(); j++)
		{
			XSSFCell oldCell = srcRow.getCell(j); // ancienne cell
			XSSFCell newCell = destRow.getCell(j); // new cell
			if (oldCell != null)
			{
				if (newCell == null)
				{
					newCell = destRow.createCell(j);
				}
				// copy chaque cell
				copyCell(oldCell, newCell, styleMap);
				// copy les informations de fusion entre les cellules
				// System.out.println("row num: " + srcRow.getRowNum() +
				// " , col: " + (short)oldCell.getColumnIndex());
				CellRangeAddress mergedRegion = getMergedRegion(srcSheet, srcRow.getRowNum(),
						(short) oldCell.getColumnIndex());

				if (mergedRegion != null)
				{
					CellRangeAddress newMergedRegion = new CellRangeAddress(mergedRegion.getFirstRow() + deltaRows,
							mergedRegion.getLastRow() + deltaRows, mergedRegion.getFirstColumn(),
							mergedRegion.getLastColumn());
					CellRangeAddressWrapper wrapper = new CellRangeAddressWrapper(newMergedRegion);
					if (isNewMergedRegion(wrapper, mergedRegions))
					{
						mergedRegions.add(wrapper);
						destSheet.addMergedRegion(wrapper.range);
					}
				}
			}
		}
	}

	/**
	 * @param oldCell
	 * @param newCell
	 * @param styleMap
	 */
	public static void copyCell(XSSFCell oldCell, XSSFCell newCell, Map<Integer, XSSFCellStyle> styleMap)
	{
		if (styleMap != null)
		{
			if (oldCell.getSheet().getWorkbook() == newCell.getSheet().getWorkbook())
			{
				newCell.setCellStyle(oldCell.getCellStyle());
			} else
			{
				int stHashCode = oldCell.getCellStyle().hashCode();
				XSSFCellStyle newCellStyle = styleMap.get(stHashCode);
				if (newCellStyle == null)
				{
					newCellStyle = newCell.getSheet().getWorkbook().createCellStyle();
					newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
					styleMap.put(stHashCode, newCellStyle);
				}
				newCell.setCellStyle(newCellStyle);
			}
		}
		switch (oldCell.getCellType())
		{
		case XSSFCell.CELL_TYPE_STRING:
			newCell.setCellValue(oldCell.getStringCellValue());
			break;
		case XSSFCell.CELL_TYPE_NUMERIC:
			newCell.setCellValue(oldCell.getNumericCellValue());
			break;
		case XSSFCell.CELL_TYPE_BLANK:
			newCell.setCellType(XSSFCell.CELL_TYPE_BLANK);
			break;
		case XSSFCell.CELL_TYPE_BOOLEAN:
			newCell.setCellValue(oldCell.getBooleanCellValue());
			break;
		case XSSFCell.CELL_TYPE_ERROR:
			newCell.setCellErrorValue(oldCell.getErrorCellValue());
			break;
		case XSSFCell.CELL_TYPE_FORMULA:
			newCell.setCellFormula(oldCell.getCellFormula());
			break;
		default:
			break;
		}

	}

	/**
	 
	 * @param sheet
	 *            the sheet containing the data.
	 * @param rowNum
	 *            the num of the row to copy.
	 * @param cellNum
	 *            the num of the cell to copy.
	 * @return the CellRangeAddress created.
	 */
	public static CellRangeAddress getMergedRegion(XSSFSheet sheet, int rowNum, short cellNum)
	{
		for (int i = 0; i < sheet.getNumMergedRegions(); i++)
		{
			CellRangeAddress merged = sheet.getMergedRegion(i);
			if (merged.isInRange(rowNum, cellNum))
			{
				return merged;
			}
		}
		return null;
	}

	/**
	 * Check that the merged region has been created in the destination sheet.
	 * 
	 * @param newMergedRegion
	 *            the merged region to copy or not in the destination sheet.
	 * @param mergedRegions
	 *            the list containing all the merged region.
	 * @return true if the merged region is already in the list or not.
	 */
	private static boolean isNewMergedRegion(CellRangeAddressWrapper newMergedRegion,
			Set<CellRangeAddressWrapper> mergedRegions)
	{
		return !mergedRegions.contains(newMergedRegion);
	}

	public void main(String sheetfrom, String sheetto) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
FileInputStream in = new FileInputStream(sheetto);
		
		XSSFWorkbook book = new XSSFWorkbook(in);
		
		ArrayList<FileInputStream> list = new ArrayList<FileInputStream>();
		
		list.add(new FileInputStream(sheetfrom));
		in.close();
		
		book = new CopySheets().mergeExcelFiles(book, list);
		
		FileOutputStream out = new FileOutputStream(sheetto);
		book.write(out);
		out.flush();
		out.close();
		
	}

	
}

class CellRangeAddressWrapper implements Comparable<CellRangeAddressWrapper>
{

	public CellRangeAddress range;

	/**
	 * @param theRange
	 *            the CellRangeAddress object to wrap.
	 */
	public CellRangeAddressWrapper(CellRangeAddress theRange)
	{
		this.range = theRange;
	}

	/**
	 * @param o
	 *            the object to compare.
	 * @return -1 the current instance is prior to the object in parameter, 0:
	 *         equal, 1: after...
	 */
	public int compareTo(CellRangeAddressWrapper o)
	{

		if (range.getFirstColumn() < o.range.getFirstColumn() && range.getFirstRow() < o.range.getFirstRow())
		{
			return -1;
		} else if (range.getFirstColumn() == o.range.getFirstColumn() && range.getFirstRow() == o.range.getFirstRow())
		{
			return 0;
		} else
		{
			return 1;
		}

	}

	/*public static void main(String[] args) throws IOException
	{
		
		System.out.println("dsfsas");
		XSSFWorkbook book = new XSSFWorkbook(new FileInputStream("C:\\BedBathandBeyond\\BedBathandBeyond\\src\\web\\mobile\\xls\\Suite_US.xlsx"));
		
	ArrayList<FileInputStream> list = new ArrayList<>();
		
		list.add(new FileInputStream("C:\\BedBathandBeyond\\BedBathandBeyond\\src\\web\\mobile\\xls\\123.xlsx"));
		
		book = new CopySheets().mergeExcelFiles(book, list);
		
		book.write(new FileOutputStream("C:\\BedBathandBeyond\\BedBathandBeyond\\src\\web\\mobile\\xls\\Suite_US.xlsx"));

	}
*/
}