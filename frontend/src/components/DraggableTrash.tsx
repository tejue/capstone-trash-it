import {TrashType} from "../types/TrashType.ts";
import styled from "styled-components";
import {CSS} from "@dnd-kit/utilities";
import {useDraggable} from "@dnd-kit/core";

type DraggableTrashProps = {
    trash: TrashType;
    index: number;
}
export default function DraggableTrash(props: Readonly<DraggableTrashProps>) {

    const {attributes, listeners, setNodeRef, transform} = useDraggable({id: props.trash.id});

    return (
        <StyledDraggableTrash
            id={props.trash.id}
            ref={setNodeRef}
            index={props.index}
            transform={transform}
            {...attributes}
            {...listeners}>
            {`${props.trash.name}`}
        </StyledDraggableTrash>
    )
}

const StyledDraggableTrash = styled.div<{
    index: number;
    transform: any;
}>`
  grid-area: ${(props) => `area${props.index}`};
  display: flex;
  align-items: center;
  justify-content: center;
  border: 1px solid white;
  padding: 8px;
  height: 100%;
  box-sizing: border-box;
  transform: ${(props) => CSS.Transform.toString(props.transform)};
`;
