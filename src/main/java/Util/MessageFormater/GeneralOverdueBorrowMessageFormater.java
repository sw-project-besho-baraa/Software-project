package Util.MessageFormater;

import Service.Book.OverdueBorrowDetection.OverdueBorrowedItemsData;

import java.util.List;

public class GeneralOverdueBorrowMessageFormater implements IMessageFormater<OverdueBorrowedItemsData>
{
    @Override
    public String formatMessage(OverdueBorrowedItemsData overdueBorrowedItemsData)
    {
        if (overdueBorrowedItemsData == null)
        {
            return "";
        }

        var user = overdueBorrowedItemsData.userContactDTO();
        List<?> items = overdueBorrowedItemsData.items();

        StringBuilder sb = new StringBuilder();

        if (user != null)
        {
            sb.append("Overdue items notice for ").append(user.getName() != null ? user.getName() : "Unknown User");
            if (user.getId() != 0)
            {
                sb.append(" (ID: ").append(user.getId()).append(")");
            }
            if (user.getEmail() != null && !user.getEmail().isEmpty())
            {
                sb.append(" - ").append(user.getEmail());
            }
            sb.append(System.lineSeparator());
        } else
        {
            sb.append("Overdue items notice").append(System.lineSeparator());
        }

        if (items == null || items.isEmpty())
        {
            sb.append("No overdue items.");
            return sb.toString();
        }

        sb.append("Total overdue items: ").append(items.size()).append(System.lineSeparator());

        for (int i = 0; i < items.size(); i++)
        {
            Object it = items.get(i);

            Object entityItem = safeInvoke(it,"item");
            String title = extractTitle(entityItem);

            long days = toLong(safeInvoke(it,"overdueDays"));
            Object detectedAt = safeInvoke(it,"detectedAt");

            sb.append(i + 1).append(". ").append("Item \"").append(title).append("\" is overdue by ").append(days)
                    .append(days == 1 ? " day" : " days");
            if (detectedAt != null)
            {
                sb.append(" (detected at ").append(detectedAt).append(")");
            }
            sb.append(System.lineSeparator());
        }

        return sb.toString().trim();
    }

    private static Object safeInvoke(Object target,String methodName)
    {
        if (target == null)
            return null;
        try
        {
            return target.getClass().getMethod(methodName).invoke(target);
        } catch (Exception e)
        {
            return null;
        }
    }

    private static long toLong(Object value)
    {
        if (value instanceof Number n)
        {
            return n.longValue();
        }
        return 0L;
    }

    private static String extractTitle(Object itemObj)
    {
        if (itemObj == null)
            return "Unknown Title";
        try
        {
            Object title = itemObj.getClass().getMethod("getTitle").invoke(itemObj);
            return title != null ? title.toString() : "Unknown Title";
        } catch (Exception e)
        {
            return "Unknown Title";
        }
    }
}
