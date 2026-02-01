package com.sms.service.interfaces;

import com.sms.exception.DataAccessException;

import java.util.List;

/**
 * ExportService
 *
 * Provides methods to export data to files (e.g., CSV).
 */
public interface ExportService<T> {

    /**
     * Export a list of data to a CSV file.
     *
     * @param data     List of data objects to export
     * @param filePath Path of the CSV file to create
     * @throws DataAccessException if writing to the file fails
     */
    void exportToCSV(List<T> data, String filePath) throws DataAccessException;
}
